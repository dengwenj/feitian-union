package vip.dengwj.feitian_union.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.RedPacketItemBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.ui.fragment.RedPackedFragment;
import vip.dengwj.feitian_union.utils.LogUtils;

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
        HomePagerContent.DataBean.ListBean listBean = list.get(position);
        TextView textView = holder.itemView.findViewById(R.id.test);
        textView.setText(listBean.getTitle());
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
