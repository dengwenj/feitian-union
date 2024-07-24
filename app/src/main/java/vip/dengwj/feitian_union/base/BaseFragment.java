package vip.dengwj.feitian_union.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = loadRootView(inflater, container, savedInstanceState);
        // 创建 Presenter
        initPresenter();
        // 加载数据
        loadData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        release();
    }

    /**
     * 销毁
     */
    public void release() {

    }

    /**
     * 创建 Presenter
     */
    public void initPresenter() {

    }

    /**
     * 加载数据
     */
    public void loadData() {

    }

    private View loadRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = loadRootViewId();
        return inflater.inflate(layoutId, container, false);
    }

    public abstract int loadRootViewId();
}
