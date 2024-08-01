package vip.dengwj.feitian_union.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.ItemHomePageBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.UrlUtils;

public class HomePagerItemAdapter extends RecyclerView.Adapter<HomePagerItemAdapter.Holder> {
    private final List<HomePagerContent.DataBean.ListBean> data = new ArrayList<>();

    private ItemHomePageBinding itemHomePageBinding;

    private OnListItemListener onListItemListener;

    // 只会创建看家视图的部分，后面会复用
    @NonNull
    @Override
    public HomePagerItemAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        itemHomePageBinding = ItemHomePageBinding.bind(view);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerItemAdapter.Holder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HomePagerContent.DataBean.ListBean> list) {
        data.clear();
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<HomePagerContent.DataBean.ListBean> list) {
        int oldSize = data.size();
        data.addAll(list);
        // 更新部分 UI
        notifyItemRangeChanged(oldSize, list.size());
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(HomePagerContent.DataBean.ListBean item) {
            Context context = itemView.getContext();

            TextView sheng = itemView.findViewById(R.id.sheng);
            TextView oldPrice = itemView.findViewById(R.id.old_price);
            TextView newPrice = itemView.findViewById(R.id.new_price);
            TextView goumai = itemView.findViewById(R.id.goumai);
            ImageView imageView = itemView.findViewById(R.id.cover);
            LinearLayout itemHomePage = itemView.findViewById(R.id.item_home_page);

            itemHomePageBinding.title.setText(item.getTitle());
            // ImageView imageView = itemHomePageBinding.cover;
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int width = layoutParams.width;
            int height = layoutParams.height;
            int size = Math.max(width, height);
            Glide.with(context).load(UrlUtils.getCoverPath(item.getCover(), size)).into(imageView);
            String format = String.format(
                    String.format(context.getString(R.string.sheng), String.format("%.2f", item.getCouponAmount()))
            );
            sheng.setText(format);
            oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            oldPrice.setText(context.getString(R.string.old_price, item.getJustPrice()));
            newPrice.setText(String.format("%.2f", Float.parseFloat(item.getZkFinalPrice())));
            goumai.setText(String.format(context.getString(R.string.goumai), item.getSellCount()));

            // 点击 item
            itemHomePage.setOnClickListener(v -> {
                if (onListItemListener != null) {
                    onListItemListener.onItemClick(item);
                }
            });
        }
    }

    public void setOnListItemListener(OnListItemListener listener) {
        onListItemListener = listener;
    }

    public interface OnListItemListener {
        void onItemClick(HomePagerContent.DataBean.ListBean item);
    }
}
