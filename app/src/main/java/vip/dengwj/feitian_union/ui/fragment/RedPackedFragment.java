package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.RedPacketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.RedPacketCallback;

public class RedPackedFragment extends BaseFragment implements RedPacketCallback {

    private RedPacketPresenter redPacketPresenter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_red_packet;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
    }

    @Override
    public void initPresenter() {
        redPacketPresenter = PresenterManager.getInstance().getRedPacketPresenter();
        redPacketPresenter.registerCallback(this);
        redPacketPresenter.getOnSellContent(false);
    }

    @Override
    public void initListener() {
        super.initListener();
    }

    /**
     * 网络错误重新加载
     */
    @Override
    public void resetNetworkError() {
        redPacketPresenter.reLoad();
    }

    @Override
    public void release() {
        redPacketPresenter.unregisterCallback(this);
    }

    /**
     * 获取特惠内容的回调
     */
    @Override
    public void onContentLoadedSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(RedPackedFragment.class, "list -> " + list);
        setupState(State.SUCCESS);
    }

    @Override
    public void onMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(RedPackedFragment.class, "list -> " + list);
    }

    @Override
    public void onMoreLoadedError() {

    }

    @Override
    public void onMoreLoadedEmpty() {

    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }
}
