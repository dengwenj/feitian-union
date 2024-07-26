package vip.dengwj.feitian_union.view;

import java.util.List;

import vip.dengwj.feitian_union.base.BaseCallback;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.LoopList;

// 回调函数 -> 跟数据做的事情
public interface CategoryPagerCallback extends BaseCallback {
    // 数据加载回来
    void onContentLoaded(List<HomePagerContent.DataBean.ListBean> list);

    int getCategoryId();

    void onLoaderMoreError();

    void onLoaderMoreEmpty();

    // 加载更多数据
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list);

    // 轮播图数据
    void onLooperListLoaded(List<LoopList.DataBean> list);
}
