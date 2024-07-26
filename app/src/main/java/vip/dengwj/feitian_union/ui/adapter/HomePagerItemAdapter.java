package vip.dengwj.feitian_union.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;

public class HomePagerItemAdapter extends RecyclerView.Adapter<HomePagerItemAdapter.Holder> {
    private final List<HomePagerContent.DataBean.ListBean> data = new ArrayList<>();

    @NonNull
    @Override
    public HomePagerItemAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_page, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePagerItemAdapter.Holder holder, int position) {

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

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
