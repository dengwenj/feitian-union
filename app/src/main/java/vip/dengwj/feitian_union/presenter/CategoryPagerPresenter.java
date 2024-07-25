package vip.dengwj.feitian_union.presenter;

public interface CategoryPagerPresenter {
    // 根据分类 id 获取内容
    void getContentByCategoryId(int categoryId);

    void loaderMore(int categoryId);

    void reload(int categoryId);
}
