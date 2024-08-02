package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.RedPacketCallback;

public interface RedPacketPresenter extends BasePresenter<RedPacketCallback> {
    /**
     * 加载特惠内容
     */
    void getOnSellContent(boolean isLoadMore);

    /**
     * 重新加载内容
     */
    void reLoad();

    /**
     * 加载更多特惠内容
     */
    void loaderMore();
}
