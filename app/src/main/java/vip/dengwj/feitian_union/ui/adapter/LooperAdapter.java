package vip.dengwj.feitian_union.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.model.domain.LoopList;
import vip.dengwj.feitian_union.utils.LogUtils;

public class LooperAdapter extends PagerAdapter {
    private final List<LoopList.DataBean> list = new ArrayList<>();

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        ImageView item = new ImageView(context);
        container.addView(item);
        // ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        // item.setLayoutParams(layoutParams);
        item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(list.get(position).getCover()).into(item);
        return item;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<LoopList.DataBean> list) {
        LogUtils.d(LooperAdapter.class, "list ->" + list);
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
}
