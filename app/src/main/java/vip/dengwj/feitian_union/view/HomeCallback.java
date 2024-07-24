package vip.dengwj.feitian_union.view;

import vip.dengwj.feitian_union.model.domain.Categories;

/**
 * 回调接口相关的类，获取数据的，类似 spring 的 dao 数据访问层
 */
public interface HomeCallback {
    void onCategoriesLoaded(Categories categories);
}
