package vip.dengwj.feitian_union.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentHomeBinding;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.presenter.HomePresenter;
import vip.dengwj.feitian_union.presenter.impl.HomePresenterImpl;
import vip.dengwj.feitian_union.ui.adapter.HomePagerAdapter;
import vip.dengwj.feitian_union.view.HomeCallback;

public class HomeFragment extends BaseFragment implements HomeCallback {

    private HomePresenter homePresenter;
    private FragmentHomeBinding fragmentHomeBinding;
    private HomePagerAdapter homePagerAdapter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_home_fragment_layout, container, false);
    }

    /**
     * 拿到视图
     */
    @Override
    public void initView(View rootView) {
        fragmentHomeBinding = FragmentHomeBinding.bind(rootView);
        fragmentHomeBinding.homeTabLayout.setupWithViewPager(fragmentHomeBinding.homeViewPager);
        homePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        fragmentHomeBinding.homeViewPager.setAdapter(homePagerAdapter);
    }

    /**
     * 创建 Presenter
     */
    @Override
    public void initPresenter() {
        homePresenter = new HomePresenterImpl();
        homePresenter.registerCallback(this);
    }

    /**
     * 加载数据
     */
    @Override
    public void loadData() {
        homePresenter.getCategories();
    }

    /**
     * 请求回来的数据
     */
    @Override
    public void onCategoriesLoaded(Categories categories) {
        setupState(State.SUCCESS);
        if (homePagerAdapter != null) {
            // 加载多少个，默认是多一个
            // fragmentHomeBinding.homeViewPager.setOffscreenPageLimit(categories.getData().size());
            homePagerAdapter.setCategory(categories);
        }
    }

    @Override
    public void onNetworkError() {
        setupState(State.ERROR);
    }

    @Override
    public void onLoading() {
        setupState(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }

    // 网络错误，请点击重试
    @Override
    public void resetNetworkError() {
        if (homePresenter != null) {
            homePresenter.getCategories();
        }
    }

    /**
     * 取消回调的注册
     */
    @Override
    public void release() {
        if (homePresenter != null) {
            homePresenter.unregisterCallback(this);
        }
    }
}
