package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

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
import vip.dengwj.feitian_union.model.domain.LoopList;
import vip.dengwj.feitian_union.presenter.CategoryPagerPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public class CategoryPagerPresenterImpl implements CategoryPagerPresenter {
    private final Map<Integer, Integer> categoryIdAndPage = new HashMap<>();

    private static final Integer defaultPage = 1;
    private Integer currentPage;

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
        for (CategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoading();
            }
        }

        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);

        // 轮播图数据
        // TODO 只请求一次
        api.getLoopList(3).enqueue(new Callback<LoopList>() {
            @Override
            public void onResponse(Call<LoopList> call, Response<LoopList> response) {
                if (response.code() == HTTP_OK) {
                    LoopList loopList = response.body();
                    for (CategoryPagerCallback callback : callbacks) {
                        if (callback.getCategoryId() == categoryId) {
                            assert loopList != null;
                            callback.onLooperListLoaded(loopList.getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoopList> call, Throwable t) {

            }
        });

        // item 内容数据
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
                if (code == HTTP_OK) {
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
            if (callback.getCategoryId() == categoryId) {
                callback.onNetworkError();
            }
        }
    }

    private void handleHomePageContentResult(HomePagerContent homePagerContent, int categoryId) {
        LogUtils.d(CategoryPagerPresenterImpl.class, "callbacks -> " + callbacks.size());
        LogUtils.d(CategoryPagerPresenterImpl.class, "homePagerContent -> " + homePagerContent);
        // 通过 categoryId 去找到
        // 通知 UI 层更新数据
        for (CategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                if (homePagerContent.getData() == null || homePagerContent.getData().getList().isEmpty()) {
                    callback.onEmpty();
                } else {
                    callback.onContentLoaded(homePagerContent.getData().getList());
                }
            }
        }
    }

    @Override
    public void loaderMore(int categoryId) {
        // 1、拿到当前页面
        currentPage = categoryIdAndPage.get(categoryId);
        // 2、页码++
        if (currentPage == null) {
            currentPage = 1;
        }
        currentPage++;
        // 3、加载数据
        String url = UrlUtils.createHomePagerUrl(categoryId, currentPage);
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        // 4、处理数据结果
        api.getHomePagerContent(url).enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                if (response.code() == HTTP_OK) {
                    HomePagerContent data = response.body();
                    handleMoreResult(categoryId, data);
                } else {
                    handleMoreError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                handleMoreError(categoryId);
            }
        });
    }

    private void handleMoreResult(int categoryId, HomePagerContent data) {
        for (CategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                if (data.getData().getList().isEmpty()) {
                    callback.onLoaderMoreEmpty();
                } else {
                    callback.onLoaderMoreLoaded(data.getData().getList());
                }
            }
        }
    }


    private void handleMoreError(int categoryId) {
        currentPage--;
        categoryIdAndPage.put(categoryId, currentPage);
        for (CategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.onLoaderMoreError();
            }
        }
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
