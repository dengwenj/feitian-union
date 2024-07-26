package vip.dengwj.feitian_union.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentHomePagerBinding;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.CategoryPagerPresenter;
import vip.dengwj.feitian_union.presenter.impl.CategoryPagerPresenterImpl;
import vip.dengwj.feitian_union.ui.adapter.HomePagerItemAdapter;
import vip.dengwj.feitian_union.utils.Constants;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public class HomePagerFragment extends BaseFragment implements CategoryPagerCallback {

    private CategoryPagerPresenter categoryPagerPresenter;
    private int materialId;
    private vip.dengwj.feitian_union.databinding.FragmentHomePagerBinding fragmentHomePagerBinding;
    private HomePagerItemAdapter pagerItemAdapter;

    public static HomePagerFragment newInstance(Categories.DataBean category, int position) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID, category.getMaterialId());
        homePagerFragment.setArguments(bundle);

        return homePagerFragment;
    }

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void initView(View rootView) {
        fragmentHomePagerBinding = FragmentHomePagerBinding.bind(rootView);

        setupState(State.SUCCESS);
    }

    @Override
    public void initPresenter() {
        assert getArguments() != null;
        Bundle arguments = getArguments();
        materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        categoryPagerPresenter = CategoryPagerPresenterImpl.getInstance();
        categoryPagerPresenter.registerCallback(this);
    }

    @Override
    public void loadData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        LogUtils.d(HomePagerFragment.class, "materialId -> " + materialId);
        categoryPagerPresenter.getContentByCategoryId(materialId);
    }

    // 销毁
    @Override
    public void release() {
        if (categoryPagerPresenter != null) {
            categoryPagerPresenter.unregisterCallback(this);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(HomePagerFragment.class, "HomePagerFragment -> list ：" + list);

        // 更新 UI
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentHomePagerBinding.homePageItem.setLayoutManager(layoutManager);
        pagerItemAdapter = new HomePagerItemAdapter();
        fragmentHomePagerBinding.homePageItem.setAdapter(pagerItemAdapter);
        pagerItemAdapter.setData(list);
        setupState(State.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return materialId;
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

    @Override
    public void onLoaderMoreError(int categoryId) {

    }

    @Override
    public void onLoaderMoreEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list, int categoryId) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean.ListBean> list, int categoryId) {

    }
}
