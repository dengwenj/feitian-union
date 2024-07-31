package vip.dengwj.feitian_union.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    private T binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setContentView(binding.getRoot());

        initView();

        initPresenter();

        initEvent();
    }

    public abstract void initPresenter();

    public void initEvent() {

    }

    public abstract void initView();

    public abstract T getBinding();
}
