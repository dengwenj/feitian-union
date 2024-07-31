package vip.dengwj.feitian_union.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseActivity;
import vip.dengwj.feitian_union.databinding.ActivityTicketBinding;
import vip.dengwj.feitian_union.utils.LogUtils;

public class TicketActivity extends BaseActivity<ActivityTicketBinding> {
    @Override
    public void initView() {
        Intent intent = getIntent();
        Bundle extrasData = intent.getExtras();
        LogUtils.d(TicketActivity.class, "extrasData -> " + extrasData.getString("title"));
    }

    @Override
    public ActivityTicketBinding getBinding() {
        return ActivityTicketBinding.inflate(getLayoutInflater());
    }
}