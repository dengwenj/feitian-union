package vip.dengwj.feitian_union.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.utils.LogUtils;

public class TicketActivity extends BaseActivity {
    @Override
    public void initView() {
        Intent intent = getIntent();
        Bundle extrasData = intent.getExtras();
        LogUtils.d(TicketActivity.class, "extrasData -> " + extrasData.getString("title"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ticket;
    }
}