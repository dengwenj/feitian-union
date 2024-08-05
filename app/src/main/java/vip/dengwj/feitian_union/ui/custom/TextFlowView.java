package vip.dengwj.feitian_union.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.utils.LogUtils;

public class TextFlowView extends ViewGroup {
    public static final int DEFAULT_SPACE = 10;

    private int horizontalSpace = DEFAULT_SPACE;
    private int verticalSpace = DEFAULT_SPACE;

    public TextFlowView(Context context) {
        this(context, null);
    }

    public TextFlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TextFlowView);

        horizontalSpace = ta.getInteger(R.styleable.TextFlowView_horizontalSpace, horizontalSpace);
        verticalSpace = ta.getInteger(R.styleable.TextFlowView_verticalSpace, verticalSpace);
        ta.recycle();
    }

    /**
     * 设置数据
     */
    public void setTextList(List<String> list) {
        for (String val : list) {
            TextView item = (TextView) LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.flow_text, this, false);
            item.setText(val);
            // 添加孩子
            addView(item);
        }
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LogUtils.d(TextFlowView.class, "horizontalSpace -> " + horizontalSpace);
        LogUtils.d(TextFlowView.class, "verticalSpace -> " + verticalSpace);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 布局，摆放孩子
    }

    public int getHorizontalSpace() {
        return horizontalSpace;
    }

    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }
}
