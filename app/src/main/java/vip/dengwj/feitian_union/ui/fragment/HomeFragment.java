package vip.dengwj.feitian_union.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.vondear.rxfeature.activity.ActivityScanerCode;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentHomeBinding;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.presenter.HomePresenter;
import vip.dengwj.feitian_union.presenter.impl.HomePresenterImpl;
import vip.dengwj.feitian_union.ui.activity.IMainActivity;
import vip.dengwj.feitian_union.ui.activity.ScannerQrCodeActivity;
import vip.dengwj.feitian_union.ui.adapter.HomePagerAdapter;
import vip.dengwj.feitian_union.view.HomeCallback;

public class HomeFragment extends BaseFragment implements HomeCallback {

    private HomePresenter homePresenter;
    private FragmentHomeBinding fragmentHomeBinding;
    private HomePagerAdapter homePagerAdapter;
    private View rootView;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_home;
    }

    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.base_home_fragment_layout, container, false);
        return rootView;
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

    @Override
    public void initListener() {
        super.initListener();
        // 点击搜索
        rootView.findViewById(R.id.edit_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity instanceof IMainActivity) {
                    ((IMainActivity) activity).switch2Search();
                }
            }
        });

        // 点击扫码
        rootView.findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ScannerQrCodeActivity.class));
            }
        });
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
            // viewPager.setOffscreenPageLimit(3); // 缓存当前页左右各2个页面，总共3个
            // 加载多少个，默认是多一个，TODO 可以进行缓存
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
