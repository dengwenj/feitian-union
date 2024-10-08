package vip.dengwj.feitian_union.view;

import java.util.List;

import vip.dengwj.feitian_union.base.BaseCallback;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;

public interface SelectedCallback extends BaseCallback {
    /**
     * 分类列表的回调
     */
    void categoryLoaded(List<SelectedCategory.DataBean> categoryList);

    /**
     * 某个分类内容的回调
     */
    void categoryContentLoaded(List<HomePagerContent.DataBean.ListBean> list);
}
