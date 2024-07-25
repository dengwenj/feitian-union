package vip.dengwj.feitian_union.view;

import vip.dengwj.feitian_union.model.domain.Categories;

/**
 * 回调接口相关的类，获取数据的
 */
public interface HomeCallback {
    void onCategoriesLoaded(Categories categories);

    void onNetworkError();

    void onLoading();

    void onEmpty();
}
