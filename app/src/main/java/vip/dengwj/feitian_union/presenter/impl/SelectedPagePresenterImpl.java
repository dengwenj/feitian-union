package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;
import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.SelectedCallback;

public class SelectedPagePresenterImpl implements SelectedPagePresenter {
    private SelectedCallback selectedCallback;

    private final API api;

    public SelectedPagePresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        api = retrofit.create(API.class);
    }

    @Override
    public void getCategory() {
        selectedCallback.onLoading();

        api.getCategory().enqueue(new Callback<SelectedCategory>() {
            @Override
            public void onResponse(Call<SelectedCategory> call, Response<SelectedCategory> response) {
                if (response.code() == HTTP_OK) {
                    SelectedCategory data = response.body();
                    if (data == null) {
                        if (selectedCallback != null) {
                            selectedCallback.onEmpty();
                        }
                        return;
                    }

                    List<SelectedCategory.DataBean> list = data.getData();
                    if (list.isEmpty()) {
                        if (selectedCallback != null) {
                            selectedCallback.onEmpty();
                        }
                    } else {
                        // 有数据
                        if (selectedCallback != null) {
                            selectedCallback.categoryLoaded(list);
                        }
                    }
                } else {
                    // 网络错误
                    if (selectedCallback != null) {
                        selectedCallback.onNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectedCategory> call, Throwable t) {
                selectedCallback.onNetworkError();
            }
        });
    }

    @Override
    public void getCategoryContent(int categoryId) {
        String url = UrlUtils.createHomePagerUrl(categoryId, 1);
        api.getHomePagerContent(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HTTP_OK) {
                    HomePagerContent data = response.body();
                    if (data == null) {
                        if (selectedCallback != null) {
                            selectedCallback.onEmpty();
                        }
                        return;
                    }

                    if (!data.isSuccess()) {
                        return;
                    }

                    List<HomePagerContent.DataBean.ListBean> list = data.getData().getList();
                    if (list.isEmpty()) {
                        if (selectedCallback != null) {
                            selectedCallback.onEmpty();
                        }
                    } else {
                        // 有数据
                        if (selectedCallback != null) {
                            selectedCallback.categoryContentLoaded(list);
                        }
                    }
                } else {
                    // 网络错误
                    if (selectedCallback != null) {
                        selectedCallback.onNetworkError();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {

            }
        });
    }

    @Override
    public void reloadContent(int categoryId) {
        if (selectedCallback != null) {
            getCategoryContent(categoryId);
        }
    }

    @Override
    public void registerCallback(SelectedCallback callback) {
        selectedCallback = callback;
    }

    @Override
    public void unregisterCallback(SelectedCallback callback) {
        selectedCallback = null;
    }
}
