package vip.dengwj.feitian_union.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.utils.LogUtils;

public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {
    private int degrees = 10;

    private boolean isRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setImageResource(R.mipmap.loading);

        post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(LoadingView.class, "loading...");
                // 不可见和 onDetachedFromWindow 就不再转动
                if (getVisibility() == GONE || !isRotate) {
                    removeCallbacks(this);
                } else {
                    degrees += 10;
                    if (degrees >= 360) {
                        degrees = 0;
                    }
                    // 才会去更新 UI
                    invalidate();
                    postDelayed(this, 30);
                }
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startRotate();
    }

    private void startRotate() {
        isRotate = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    private void stopRotate() {
        isRotate = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // (float) getWidth() / 2, (float) getHeight() / 2 根据圆心转动
        canvas.rotate(degrees, (float) getWidth() / 2, (float) getHeight() / 2);
        super.onDraw(canvas);
    }
}
