package vip.dengwj.feitian_union.ui.activity;

import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.databinding.ActivityTicketBinding;
import vip.dengwj.feitian_union.model.domain.Ticket;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.TicketCallback;

public class TicketActivity extends BaseActivity<ActivityTicketBinding> implements TicketCallback {

    private TicketPresenter ticketPresenter;

    @Override
    public void initPresenter() {
        ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.registerCallback(this);
    }

    @Override
    public void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ticketPresenter.unregisterCallback(this);
    }

    @Override
    public ActivityTicketBinding getBinding() {
        return ActivityTicketBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onTicketLoaded(String cover, Ticket result) {
        LogUtils.d(TicketActivity.class, "cover -> " + cover);
        LogUtils.d(TicketActivity.class, "result -> " + result);
    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}