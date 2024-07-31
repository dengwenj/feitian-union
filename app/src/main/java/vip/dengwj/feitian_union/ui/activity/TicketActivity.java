package vip.dengwj.feitian_union.ui.activity;

import android.view.View;

import com.bumptech.glide.Glide;

import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.databinding.ActivityTicketBinding;
import vip.dengwj.feitian_union.model.domain.Ticket;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.TicketCallback;

public class TicketActivity extends BaseActivity<ActivityTicketBinding> implements TicketCallback {

    private TicketPresenter ticketPresenter;
    private ActivityTicketBinding ticketBinding;

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
        ticketBinding = ActivityTicketBinding.inflate(getLayoutInflater());
        return ticketBinding;
    }

    @Override
    public void initEvent() {
        ticketBinding.back.setOnClickListener(v -> finish());

        ticketBinding.ticketLq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TicketActivity.class, "点击");
            }
        });
    }

    @Override
    public void onTicketLoaded(String cover, Ticket result) {
        ticketBinding.loading.setVisibility(View.GONE);
        ticketBinding.error.setVisibility(View.GONE);

        String model = result.getData().getTbk_tpwd_create_response().getData().getModel();
        String[] split = model.split("￥");
        Glide.with(this).load(UrlUtils.getCoverPath(cover, 300)).into(ticketBinding.ticketImg);
        ticketBinding.ticketCode.setText(String.format("￥" + split[1] + "￥"));
        ticketBinding.ticketLq.setEnabled(true);
    }

    @Override
    public void onNetworkError() {
        ticketBinding.error.setVisibility(View.VISIBLE);
        ticketBinding.loading.setVisibility(View.GONE);
    }

    @Override
    public void onLoading() {
        ticketBinding.error.setVisibility(View.GONE);
        ticketBinding.loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmpty() {

    }
}