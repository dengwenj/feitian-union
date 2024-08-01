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
import vip.dengwj.feitian_union.databinding.ItemSelectedLeftBinding;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;

public class SelectedLeftAdapter extends RecyclerView.Adapter<SelectedLeftAdapter.Holder> {
    private final List<SelectedCategory.DataBean> list = new ArrayList<>();
    private ItemSelectedLeftBinding selectedLeftBinding;
    private int currentPosition = 0;

    private OnCategoryLeftClickListener onCategoryLeftClickListener;

    @NonNull
    @Override
    public SelectedLeftAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        selectedLeftBinding = ItemSelectedLeftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(selectedLeftBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedLeftAdapter.Holder holder, int position) {
        SelectedCategory.DataBean dataBean = list.get(position);
        holder.setData(dataBean, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<SelectedCategory.DataBean> categoryList) {
        list.clear();
        list.addAll(categoryList);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_title);
            textView.setOnClickListener(v -> {
                currentPosition = (Integer) v.getTag(R.string.position);
                notifyDataSetChanged();
                if (onCategoryLeftClickListener != null) {
                    Integer categoryId = (Integer) v.getTag(R.string.category_id);
                    onCategoryLeftClickListener.onCategoryLeftClick(categoryId);
                }
            });
        }

        public void setData(SelectedCategory.DataBean dataBean, int position) {
            textView.setText(dataBean.getMaterialName());
            // 做个标识
            textView.setTag(R.string.position, position);
            textView.setTag(R.string.category_id, dataBean.getMaterialId());

            if (position == currentPosition) {
                textView.setBackgroundColor(
                        textView.getResources().getColor(R.color.category_left)
                );
            } else {
                textView.setBackgroundColor(
                        textView.getResources().getColor(R.color.white)
                );
            }
        }
    }

    public void setOnCategoryLeftClickListener(OnCategoryLeftClickListener listener) {
        onCategoryLeftClickListener = listener;
    }

    public interface OnCategoryLeftClickListener {
        void onCategoryLeftClick(int categoryId);
    }
}
