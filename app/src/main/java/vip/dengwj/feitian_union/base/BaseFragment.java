package vip.dengwj.feitian_union.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.databinding.FragmentNetworlErrorBinding;

public abstract class BaseFragment extends Fragment {
    private State currentState = State.NONE;
    private View successView;
    private View loadingView;
    private View networkErrorView;
    private View emptyView;
    private FrameLayout frameLayout;
    private FragmentNetworlErrorBinding networlErrorBinding;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = loadRootView(inflater, container);
        frameLayout = rootView.findViewById(R.id.base_fragment_layout);
        View view = loadStateView(inflater, container);

        // 创建视图
        initView(view);
        // 创建 Presenter
        initPresenter();
        // 加载数据
        loadData();
        // 监听事件
        initListener();
        // 页面展示的视图
        return rootView;
    }

    private void initListener() {
        networlErrorBinding = FragmentNetworlErrorBinding.bind(networkErrorView);
        networlErrorBinding.networkError.setOnClickListener(v -> {
            resetNetworkError();
        });
    }

    // 网络错误，请点击重试，子类可以覆写
    public void resetNetworkError() {

    }

    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_fragment_layout, container, false);
    }

    private View loadStateView(LayoutInflater inflater, ViewGroup container) {
        // 成功视图
        successView = loadSuccessView(inflater, container);
        frameLayout.addView(successView);
        // 加载中视图
        loadingView = loadLoadingView(inflater, container);
        frameLayout.addView(loadingView);
        // 网络错误视图
        networkErrorView = loadNetworkErrorView(inflater, container);
        frameLayout.addView(networkErrorView);
        // 暂无数据视图
        emptyView = loadEmptyView(inflater, container);
        frameLayout.addView(emptyView);

        setupState(currentState);
        return successView;
    }

    // 暂无数据视图
    private View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty, container, false);
    }

    // 网络错误视图
    private View loadNetworkErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_networl_error, container, false);
    }

    // 加载中视图
    private View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    // 根据状态显示隐藏
    protected void setupState(State state) {
        successView.setVisibility(State.SUCCESS == state ? View.VISIBLE : View.GONE);
        loadingView.setVisibility(State.LOADING == state ? View.VISIBLE : View.GONE);
        networkErrorView.setVisibility(State.ERROR == state ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(State.EMPTY == state ? View.VISIBLE : View.GONE);
    }

    public void initView(View rootView) {

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

    /**
     * 成功视图
     */
    private View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int layoutId = loadRootViewId();
        return inflater.inflate(layoutId, container, false);
    }

    public abstract int loadRootViewId();
}
