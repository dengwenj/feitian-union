package vip.dengwj.feitian_union.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.bumptech.glide.Glide;

import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.databinding.ActivityTicketBinding;
import vip.dengwj.feitian_union.model.domain.Ticket;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.utils.ToastUtils;
import vip.dengwj.feitian_union.utils.UrlUtils;
import vip.dengwj.feitian_union.view.TicketCallback;

public class TicketActivity extends BaseActivity<ActivityTicketBinding> implements TicketCallback {

    private TicketPresenter ticketPresenter;
    private ActivityTicketBinding ticketBinding;

    private boolean hasTaobaoApp = false;

    private String code;

    @Override
    public void initPresenter() {
        ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.registerCallback(this);

        // 检查是否安装淘宝应用
        // 淘宝包名：com.taobao.taobao
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo("com.taobao.taobao", PackageManager.MATCH_UNINSTALLED_PACKAGES);
            hasTaobaoApp = packageInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            hasTaobaoApp = false;
        }
        LogUtils.d(TicketActivity.class, "hasTaobaoApp -》 " + hasTaobaoApp);

        ticketBinding.ticketLq.setText(hasTaobaoApp ? "打开淘宝领券" : "复制淘口令");
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

        ticketBinding.ticketLq.setOnClickListener(v -> {
            // 复制到粘贴板
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("ft_taobao_ticket_code", code);
            cm.setPrimaryClip(data);
            // 判断是否有淘宝
            if (hasTaobaoApp) {
                // 打开淘宝
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.taobao.taobao");
                startActivity(intent);
            } else {
                ToastUtils.showToast("已复制，粘贴分享，或打开淘宝");
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
        code = String.format("￥" + split[1] + "￥");
        ticketBinding.ticketCode.setText(code);
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