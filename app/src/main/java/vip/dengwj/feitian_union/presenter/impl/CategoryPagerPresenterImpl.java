package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.CategoryPagerPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public class CategoryPagerPresenterImpl implements CategoryPagerPresenter {
    private final Map<Integer, Integer> categoryIdAndPage = new HashMap<>();

    private static final Integer defaultPage = 1;

    // 单例
    private CategoryPagerPresenterImpl() {

    }

    private static CategoryPagerPresenter instance = null;

    public static CategoryPagerPresenter getInstance(int categoryId, int position) {
        if (instance == null) {
            instance = new CategoryPagerPresenterImpl();
        }
        return instance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        for (CategoryPagerCallback callback : callbacks) {
            callback.onLoading(categoryId);
        }

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        // 没有该 categoryId 才添加
        Integer targetPage = categoryIdAndPage.get(categoryId);
        if (targetPage == null) {
            targetPage = defaultPage;
            categoryIdAndPage.put(categoryId, targetPage);
        } else {
            // targetPage++;
            categoryIdAndPage.replace(categoryId, targetPage);
        }
        // url
        String url = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        api.getHomePagerContent(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(CategoryPagerPresenterImpl.class, "code -> " + code);
                if (response.code() == HTTP_OK) {
                    HomePagerContent homePagerContent = response.body();
                    assert homePagerContent != null;
                    handleHomePageContentResult(homePagerContent, categoryId);
                } else {
                    handleNetworkError(categoryId);
                    LogUtils.d(CategoryPagerPresenterImpl.class, "出错啦11");
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LogUtils.d(CategoryPagerPresenterImpl.class, "出错啦22" + t.getMessage());
            }
        });
    }

    private void handleNetworkError(int categoryId) {
        for (CategoryPagerCallback callback : callbacks) {
            callback.onError(categoryId);
        }
    }

    private void handleHomePageContentResult(HomePagerContent homePagerContent, int categoryId) {
        LogUtils.d(CategoryPagerPresenterImpl.class, "callbacks -> " + callbacks.size());
        LogUtils.d(CategoryPagerPresenterImpl.class, "homePagerContent -> " + homePagerContent);
        // 通过 categoryId 去找到
        // 通知 UI 层更新数据
        for (CategoryPagerCallback callback : callbacks) {
            if (homePagerContent.getData() == null || homePagerContent.getData().getList().isEmpty()) {
                callback.onEmpty(categoryId);
            } else {
                callback.onContentLoaded(homePagerContent.getData().getList(), categoryId);
            }
        }
    }

    @Override
    public void loaderMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private final List<CategoryPagerCallback> callbacks = new ArrayList<>();

    @Override
    public void registerCallback(CategoryPagerCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    @Override
    public void unregisterCallback(CategoryPagerCallback callback) {
        callbacks.remove(callback);
    }
}
