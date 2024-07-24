package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

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
        if (homePagerAdapter != null) {
            homePagerAdapter.setCategory(categories);
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
