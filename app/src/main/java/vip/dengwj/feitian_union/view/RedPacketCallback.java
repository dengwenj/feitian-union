package vip.dengwj.feitian_union.view;

import java.util.List;

import vip.dengwj.feitian_union.base.BaseCallback;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;

public interface RedPacketCallback extends BaseCallback {
    /**
     * 特惠内容加载完成
     */
    void onContentLoadedSuccess(List<HomePagerContent.DataBean.ListBean> list);

    /**
     * 加载更多的结果
     */
    void onMoreLoaded(List<HomePagerContent.DataBean.ListBean> list);

    /**
     * 加载更多失败
     */
    void onMoreLoadedError();

    /**
     * 没有更多內容
     */
    void onMoreLoadedEmpty();
}
