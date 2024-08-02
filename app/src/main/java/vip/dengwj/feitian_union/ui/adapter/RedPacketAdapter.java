package vip.dengwj.feitian_union.ui.adapter;

import android.graphics.Paint;
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
import vip.dengwj.feitian_union.databinding.RedPacketItemBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.ui.fragment.RedPackedFragment;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.UrlUtils;

public class RedPacketAdapter extends RecyclerView.Adapter<RedPacketAdapter.Holder> {
    private final List<HomePagerContent.DataBean.ListBean> list = new ArrayList<>();

    @NonNull
    @Override
    public RedPacketAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RedPacketItemBinding redPacketItemBinding = RedPacketItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(redPacketItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RedPacketAdapter.Holder holder, int position) {
        View itemView = holder.itemView;
        HomePagerContent.DataBean.ListBean listBean = list.get(position);

        ImageView imageView = itemView.findViewById(R.id.img);
        TextView title = itemView.findViewById(R.id.title);
        TextView oldPrice = itemView.findViewById(R.id.old_price);
        TextView newPrice = itemView.findViewById(R.id.new_price);

        Glide.with(itemView.getContext()).load(UrlUtils.getCoverPath(listBean.getCover(), 200)).into(imageView);
        title.setText(listBean.getTitle());
        String oldP = "￥" + String.format("%.2f", Float.parseFloat(listBean.getJustPrice()));
        oldPrice.setText(oldP);
        oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        String newP = "券后价：" + String.format("%.2f", Float.parseFloat(listBean.getZkFinalPrice()));
        newPrice.setText(newP);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<HomePagerContent.DataBean.ListBean> list) {
        this.list.clear();
        this.list.addAll(list);
        LogUtils.d(RedPackedFragment.class, "list size -> " + this.list.size());
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
