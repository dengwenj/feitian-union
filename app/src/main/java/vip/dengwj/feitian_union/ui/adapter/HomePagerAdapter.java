package vip.dengwj.feitian_union.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.ui.fragment.HomePagerFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private final List<Categories.DataBean> list = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new HomePagerFragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }

    public void setCategory(Categories categories) {
        list.clear();
        list.addAll(categories.getData());
        // 更新 UI
        notifyDataSetChanged();
    }
}
