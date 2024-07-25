package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import java.util.HashMap;
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

    private CategoryPagerCallback categoryPagerCallback;

    // 单例
    private CategoryPagerPresenterImpl() {

    }

    private static CategoryPagerPresenter instance = null;

    public static CategoryPagerPresenter getInstance() {
        if (instance == null) {
            instance = new CategoryPagerPresenterImpl();
        }
        return instance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
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
        LogUtils.d(CategoryPagerPresenterImpl.class, "url -> " + url);
        api.getHomePagerContent(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(CategoryPagerPresenterImpl.class, "code -> " + code);
                if (response.code() == HTTP_OK) {
                    HomePagerContent homePagerContent = response.body();
                    assert homePagerContent != null;
                    if (homePagerContent.isSuccess()) {
                        if (categoryPagerCallback == null) return;
                        categoryPagerCallback.onContentLoaded(homePagerContent.getData().getList());
                    } else {
                        LogUtils.d(CategoryPagerPresenterImpl.class, "出错啦00");
                    }
                } else {
                    LogUtils.d(CategoryPagerPresenterImpl.class, "出错啦11");
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LogUtils.d(CategoryPagerPresenterImpl.class, "出错啦22" + t.getMessage());
            }
        });
    }

    @Override
    public void loaderMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    @Override
    public void registerCallback(CategoryPagerCallback callback) {
        categoryPagerCallback = callback;
    }

    @Override
    public void unregisterCallback(CategoryPagerCallback callback) {
        categoryPagerCallback = null;
    }
}
