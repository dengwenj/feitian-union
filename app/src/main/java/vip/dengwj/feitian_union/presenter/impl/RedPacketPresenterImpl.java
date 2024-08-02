package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.RedPacketPresenter;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.RedPacketCallback;

public class RedPacketPresenterImpl implements RedPacketPresenter {
    private Integer page = 1;

    private RedPacketCallback redPacketCallback;

    // 还在加载中不能再去加载
    private boolean isLoading = false;

    @Override
    public void getOnSellContent(boolean isLoadMore) {
        if (isLoading) {
            return;
        }
        isLoading = true;
        // 加载更多时不 Loading
        if (!isLoadMore) {
            handleLoading();
        }

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        String url = UrlUtils.youLikeUrl(page);
        api.getYouLikeContent(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HTTP_OK) {
                    // 正确
                    HomePagerContent data = response.body();
                    if (data == null) {
                        // 是否为加载更多
                        if (isLoadMore) {
                            handleLoadMoreEmpty();
                        } else {
                            handleEmpty();
                        }
                        return;
                    }

                    List<HomePagerContent.DataBean.ListBean> list = data.getData().getList();
                    if (list.isEmpty()) {
                        if (isLoadMore) {
                            handleLoadMoreEmpty();
                        } else {
                            handleEmpty();
                        }
                        return;
                    }

                    // 有数据
                    if (isLoadMore) {
                        handleLoadMoreSuccess(list);
                    } else {
                        handleSuccess(list);
                    }
                } else {
                    // 错误
                    if (isLoadMore) {
                        handleLoadMoreError();
                    } else {
                        handleError();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                if (isLoadMore) {
                    handleLoadMoreError();
                } else {
                    handleError();
                }
            }
        });
    }

    private void handleLoadMoreError() {
        isLoading = false;
        page--;
        if (redPacketCallback == null) return;
        redPacketCallback.onMoreLoadedError();
    }

    private void handleLoadMoreSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        isLoading = false;
        if (redPacketCallback == null) return;
        redPacketCallback.onMoreLoaded(list);
    }

    private void handleLoadMoreEmpty() {
        isLoading = false;
        if (redPacketCallback == null) return;
        redPacketCallback.onMoreLoadedEmpty();
    }

    private void handleSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        isLoading = false;
        if (redPacketCallback == null) return;
        redPacketCallback.onContentLoadedSuccess(list);
    }

    private void handleEmpty() {
        isLoading = false;
        if (redPacketCallback == null) return;
        redPacketCallback.onEmpty();
    }

    private void handleLoading() {
        if (redPacketCallback == null) return;
        redPacketCallback.onLoading();
    }

    private void handleError() {
        isLoading = false;
        if (redPacketCallback == null) return;
        redPacketCallback.onNetworkError();
    }

    @Override
    public void reLoad() {
        page = 1;
        getOnSellContent(false);
    }

    @Override
    public void loaderMore() {
        page++;
        getOnSellContent(true);
    }

    @Override
    public void registerCallback(RedPacketCallback callback) {
        redPacketCallback = callback;
    }

    @Override
    public void unregisterCallback(RedPacketCallback callback) {
        redPacketCallback = null;
    }
}
