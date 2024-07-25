package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public interface CategoryPagerPresenter extends BasePresenter<CategoryPagerCallback> {
    // 根据分类 id 获取内容
    void getContentByCategoryId(int categoryId);

    void loaderMore(int categoryId);

    void reload(int categoryId);


}
