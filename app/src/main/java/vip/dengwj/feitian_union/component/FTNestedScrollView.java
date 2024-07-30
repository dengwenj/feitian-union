package vip.dengwj.feitian_union.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import vip.dengwj.feitian_union.utils.LogUtils;

public class FTNestedScrollView extends NestedScrollView {
    private int scrollTop;
    private int headerHeight;

    public FTNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public FTNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FTNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderHeight(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        LogUtils.d(FTNestedScrollView.class, "scrollTop -> " + scrollTop);
        LogUtils.d(FTNestedScrollView.class, "headerHeight -> " + headerHeight);


        if (scrollTop < headerHeight) {
            scrollBy(dx, dy);
            // 消耗掉
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        scrollTop = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
