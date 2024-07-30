package vip.dengwj.feitian_union.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initView();

        initEvent();
    }

    public void initEvent() {

    }

    public abstract void initView();

    public abstract int getLayoutId();
}
