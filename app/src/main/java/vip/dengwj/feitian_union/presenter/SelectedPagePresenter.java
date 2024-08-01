package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.SelectedCallback;

public interface SelectedPagePresenter extends BasePresenter<SelectedCallback> {
    /**
     * 获取分类
     */
    void getCategory();

    /**
     * 根据分类获取内容
     */
    void getCategoryContent(int categoryId);

    /**
     *  重新加载内容
     */
    void reloadContent();
}
