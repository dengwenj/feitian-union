package vip.dengwj.feitian_union.ui.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class AutoLoopViewPager extends ViewPager {
    private boolean isLoop;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            if (isLoop) {
                postDelayed(this, 3000);
            }
        }
    };

    public AutoLoopViewPager(@NonNull Context context) {
        super(context);
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 开始轮播
    public void startLoop() {
        isLoop = true;
        post(runnable);
    }

    // 停止轮播
    public void stopLoop() {
        isLoop = false;
        removeCallbacks(runnable);
    }
}
