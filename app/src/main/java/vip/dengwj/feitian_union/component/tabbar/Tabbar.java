package vip.dengwj.feitian_union.component.tabbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.ControlTabbarBinding;

public class Tabbar extends RelativeLayout {

    private ControlTabbarBinding controlTabbarBinding;
    private OnCheckedChangeListener onCheckedChangeListener;

    public Tabbar(Context context) {
        this(context, null);
    }

    public Tabbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Tabbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化视图
        initView();
        // 事件监听
        initListener();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.control_tabbar, this, true);
        controlTabbarBinding = ControlTabbarBinding.bind(view);
    }

    private void initListener() {
        controlTabbarBinding.nav.setOnCheckedChangeListener((group, checkedId) -> {
            if (onCheckedChangeListener == null) return;
            onCheckedChangeListener.onCheckedChange(group, checkedId);
        });
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        onCheckedChangeListener = listener;
    }

    public RadioGroup getTabBarView() {
        return controlTabbarBinding.nav;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(RadioGroup group, int checkedId);
    }
}
