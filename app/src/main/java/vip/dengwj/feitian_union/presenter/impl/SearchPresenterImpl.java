package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.Recommend;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.view.SearchCallback;

public class SearchPresenterImpl implements SearchPresenter {

    private final API api;

    private SearchCallback searchCallback;

    public SearchPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        api = retrofit.create(API.class);
    }

    @Override
    public void getHistories() {

    }

    @Override
    public void deleteHistory() {

    }

    @Override
    public void doSearch(String keyword) {

    }

    @Override
    public void research() {

    }

    @Override
    public void loaderMore() {

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
