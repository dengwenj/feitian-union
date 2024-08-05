package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.HistoryWord;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.Recommend;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.utils.JsonCacheUtil;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.view.SearchCallback;

public class SearchPresenterImpl implements SearchPresenter {

    private final API api;
    private final JsonCacheUtil jsonCacheUtil;

    private Integer page = 1;

    private SearchCallback searchCallback;
    private String keyword;

    public static final String keyHistory = "key_history";
    private static final Integer historySize = 10;

    public SearchPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        api = retrofit.create(API.class);
        jsonCacheUtil = JsonCacheUtil.getInstance();
    }

    @Override
    public void getHistories() {
        if (searchCallback != null) {
            HistoryWord val = jsonCacheUtil.getVal(keyHistory, HistoryWord.class);
            searchCallback.onHistoryLoaded(val.getHistoryList());
        }

    }

    @Override
    public void deleteHistory() {
        if (searchCallback != null) {
            jsonCacheUtil.delVal(keyHistory);
            searchCallback.onHistoryDeleted();
        }
    }

    private void saveHistory(String history) {
        // 如果已经存在就删除，然后在保存
        HistoryWord historyWord = jsonCacheUtil.getVal(keyHistory, HistoryWord.class);
        if (historyWord == null) {
            historyWord = new HistoryWord();
        }
        List<String> historyList = historyWord.getHistoryList();
        if (historyList == null) {
            historyList = new ArrayList<>();
        }
        // 先删除
        historyList.remove(history);
        // 再保存
        historyList.add(0, history);
        // 对个数进行限制
        if (historyList.size() > historySize) {
            historyList.subList(0, historySize);
        }
        historyWord.setHistoryList(historyList);
        jsonCacheUtil.saveVal(keyHistory, historyWord);
    }

    /**
     * 搜索
     */
    @Override
    public void doSearch(String keyword, boolean isLoadMore) {
        if (this.keyword == null || !this.keyword.equals(keyword)) {
            saveHistory(keyword);
            this.keyword = keyword;
        }
        String url = "/shop/s/" + page + "?k=" + keyword;
        api.getSearchList(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HTTP_OK) {
                    assert response.body() != null;

                    // 上拉加载
                    if (isLoadMore) {
                        handleLoadMoreSearchSuccess(response.body().getData().getList());
                    } else {
                        // 搜索
                        handleDoSearchSuccess(response.body().getData().getList());
                    }
                } else {
                    if (isLoadMore) {
                        handleLoadMoreError();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                if (isLoadMore) {
                    handleLoadMoreError();
                }
            }
        });
    }

    private void handleLoadMoreError() {
        if (searchCallback != null) {
            searchCallback.onMoreLoadedError();
        }
    }

    private void handleLoadMoreSearchSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        if (searchCallback != null) {
            if (list.isEmpty()) {
                searchCallback.onMoreLoadedEmpty();
            } else {
                searchCallback.onMoreLoaded(list);
            }
        }
    }

    private void handleDoSearchSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        if (searchCallback != null) {
            searchCallback.onSearchSuccess(list);
        }
    }

    @Override
    public void research() {
        doSearch(keyword, false);
    }

    @Override
    public void loaderMore() {
        page++;
        doSearch(keyword, true);
    }

    /**
     * 获取推荐词
     */
    @Override
    public void getRecommendWords() {
        handleLoading();
        api.getRecommend().enqueue(new Callback<Recommend>() {
            @Override
            public void onResponse(Call<Recommend> call, Response<Recommend> response) {
                if (response.code() == HTTP_OK) {
                    Recommend data = response.body();
                    assert data != null;
                    if (!data.isSuccess()) {
                        handleError();
                        return;
                    }

                    List<Recommend.DataBean> list = data.getData();
                    if (list != null) {
                        List<String> wordList = list.stream()
                                .map(Recommend.DataBean::getWord)
                                .collect(Collectors.toList());
                        handleSuccess(wordList);
                    }
                } else {
                    handleError();
                }
            }

            @Override
            public void onFailure(Call<Recommend> call, Throwable t) {
                handleError();
            }
        });
    }

    private void handleSuccess(List<String> list) {
        if (searchCallback != null) {
            searchCallback.onRecommendWordsLoaded(list);
        }
    }

    private void handleError() {
        if (searchCallback != null) {
            searchCallback.onNetworkError();
        }
    }

    private void handleLoading() {
        if (searchCallback != null) {
            searchCallback.onLoading();
        }
    }

    @Override
    public void registerCallback(SearchCallback callback) {
        searchCallback = callback;
    }

    @Override
    public void unregisterCallback(SearchCallback callback) {
        searchCallback = null;
    }
}
