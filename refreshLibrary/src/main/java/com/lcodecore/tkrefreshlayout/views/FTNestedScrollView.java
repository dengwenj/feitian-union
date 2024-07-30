package com.lcodecore.tkrefreshlayout.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class FTNestedScrollView extends NestedScrollView {
    private int scrollTop;
    private int headerHeight;
    private RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) target;
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

    public boolean isInBottom() {
        if (recyclerView != null) {
            return !recyclerView.canScrollVertically(1);
        }
        return false;
    }
}
