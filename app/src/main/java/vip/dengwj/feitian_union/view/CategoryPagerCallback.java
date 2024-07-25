package vip.dengwj.feitian_union.view;

import java.util.List;

import vip.dengwj.feitian_union.model.domain.HomePagerContent;

// 回调函数 -> 跟数据做的事情
public interface CategoryPagerCallback {
    // 数据加载回来
    void onContentLoaded(List<HomePagerContent.DataBean.ListBean> list);

    // 加载中
    void onLoading(int categoryId);

    // 加载出错
    void onError(int categoryId);

    // 暂无数据
    void onEmpty(int categoryId);

    void onLoaderMoreError(int categoryId);

    void onLoaderMoreEmpty(int categoryId);

    // 加载更多数据
    void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list);

    // 轮播图数据
    void onLooperListLoaded(List<HomePagerContent.DataBean.ListBean> list);
}
