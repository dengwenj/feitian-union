package vip.dengwj.feitian_union.view;

import java.util.List;

import vip.dengwj.feitian_union.base.BaseCallback;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;

public interface SearchCallback extends BaseCallback {
    /**
     * 搜索历史结果
     */
    void onHistoryLoaded(List<String> historyList);

    /**
     * 历史记录删除完成
     */
    void onHistoryDeleted();

    /**
     * 搜索结果成功
     */
    void onSearchSuccess(List<HomePagerContent.DataBean.ListBean> list);

    /**
     * 加载到了更多内容
     */
    void onMoreLoaded(List<HomePagerContent.DataBean.ListBean> list);

    /**
     * 加载更多时网络错误
     */
    void onMoreLoadedError();

    /**
     * 没有更多内容
     */
    void onMoreLoadedEmpty();

    /**
     * 获取到搜索关键字
     */
    void onRecommendWordsLoaded(List<String> recommendWords);
}
