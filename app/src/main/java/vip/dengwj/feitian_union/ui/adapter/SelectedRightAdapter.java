package vip.dengwj.feitian_union.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.ItemSelectedRightBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.UrlUtils;

public class SelectedRightAdapter extends RecyclerView.Adapter<SelectedRightAdapter.Holder> {
    private final List<HomePagerContent.DataBean.ListBean> list = new ArrayList<>();
    private vip.dengwj.feitian_union.databinding.ItemSelectedRightBinding selectedRightBinding;

    @NonNull
    @Override
    public SelectedRightAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtils.d(SelectedRightAdapter.class, "多次");
        selectedRightBinding = ItemSelectedRightBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(selectedRightBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedRightAdapter.Holder holder, int position) {
        HomePagerContent.DataBean.ListBean item = list.get(position);
        View view = holder.itemView;
        TextView sheng1 = view.findViewById(R.id.sheng1);
        TextView text = view.findViewById(R.id.text);
        TextView yj = view.findViewById(R.id.yj);
        ImageView imageView = view.findViewById(R.id.img);
        Glide.with(holder.itemView.getContext()).load(UrlUtils.getCoverPath(item.getCover(), 200)).into(imageView);
        sheng1.setText("领券省" + String.format("%.2f", item.getCouponAmount()) + "元");
        text.setText(item.getTitle());
        yj.setText("原价：" + String.format("%.2f", item.getJustPrice()) + "元");
    }

    // TODO：数据错乱问题
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(SelectedLeftAdapter.class, "size -> " + list.size());
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
