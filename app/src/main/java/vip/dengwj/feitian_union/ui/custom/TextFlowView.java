package vip.dengwj.feitian_union.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.utils.LogUtils;

public class TextFlowView extends ViewGroup {
    public static final int DEFAULT_SPACE = 10;

    private int horizontalSpace = DEFAULT_SPACE;
    private int verticalSpace = DEFAULT_SPACE;
    private int parentWidth;

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

    // 每一行
    private List<View> line = new ArrayList<>();
    // 里面 item 就是每一行
    private List<List<View>> lineList = new ArrayList<>();
    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // widthMeasureSpec 两部分组成，一个模式，一个值
        // 获取值
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);

        // 测量孩子
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = getChildAt(i);
            // 给孩子测量宽度
            measureChild(itemView, widthMeasureSpec, heightMeasureSpec);

            // 如果当前行是空直接添加进去
            if (line.isEmpty()) {
                createNewLine(itemView);
            } else {
                // 当前行不为空判断能是否添加当该行
                if (canBeAdd(itemView, line)) {
                    // 可以继续添加
                    line.add(itemView);
                } else {
                    // 创建新的一行
                    createNewLine(itemView);
                }
            }
        }

        // 测量自己
        // itemView 的高度
        int measuredHeight = getChildAt(0).getMeasuredHeight();
        int lineSize = lineList.size();
        setMeasuredDimension(
                parentWidth,
                measuredHeight * lineSize + verticalSpace * lineSize
        );
    }

    private void createNewLine(View itemView) {
        line.add(itemView);
        lineList.add(line);
    }

    /**
     * 判断当前行是否可以再添加
     * @param itemView
     * @param line
     */
    private boolean canBeAdd(View itemView, List<View> line) {
        // 判断条件：当前行里的 item + 间距 + itemView 小于等于，屏幕宽度可以添加，反之
        int totalWidth = itemView.getMeasuredWidth();
        for (int i = 0; i < line.size(); i++) {
            totalWidth += line.get(i).getMeasuredWidth();
        }
        // 间距
        totalWidth += (line.size() + 1) * horizontalSpace;

        return totalWidth <= parentWidth;
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
