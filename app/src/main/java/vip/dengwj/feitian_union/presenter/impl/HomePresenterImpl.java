package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.presenter.HomePresenter;
import vip.dengwj.feitian_union.utils.MaterialList;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.view.HomeCallback;

/**
 * presenter 处理逻辑的地方，类似 spring 的 services 架构
 */
public class HomePresenterImpl implements HomePresenter {
    private HomeCallback homeCallback;

    @Override
    public void getCategories() {
        // 先加载
        homeCallback.onLoading();

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                // 请求成功
                if (response.code() == HTTP_OK) {
                    Categories categories = response.body();
                    if (categories == null || categories.getData().isEmpty()) {
                        homeCallback.onEmpty();
                    } else {
                        List<Categories.DataBean> dataBeans = MaterialList.getMaterialList();
                        categories.setData(dataBeans);
                        homeCallback.onCategoriesLoaded(categories);
                    }
                } else {
                    // 请求失败
                    homeCallback.onNetworkError();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                // 请求失败
                homeCallback.onNetworkError();
            }
        });
    }

    @Override
    public void registerCallback(HomeCallback callback) {
        homeCallback = callback;
    }

    @Override
    public void unregisterCallback(HomeCallback callback) {
        homeCallback = callback;
    }
}
