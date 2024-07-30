package vip.dengwj.feitian_union.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import vip.dengwj.feitian_union.R;

public class AutoLoopViewPager extends ViewPager {
    public static final int DEFAULT_DURATION = 3000;

    private int mDuration = DEFAULT_DURATION;

    private boolean isLoop;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            if (isLoop) {
                postDelayed(this, mDuration);
            }
        }
    };

    public AutoLoopViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoLoopViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLoopViewPager);

        mDuration = typedArray.getInt(R.styleable.AutoLoopViewPager_duration, mDuration);

        typedArray.recycle();
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
