package vip.dengwj.feitian_union.ui.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.CategoryPagerPresenter;
import vip.dengwj.feitian_union.presenter.impl.CategoryPagerPresenterImpl;
import vip.dengwj.feitian_union.utils.Constants;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public class HomePagerFragment extends BaseFragment implements CategoryPagerCallback {

    private CategoryPagerPresenter categoryPagerPresenter;

    public static HomePagerFragment newInstance(Categories.DataBean category) {
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
        setupState(State.SUCCESS);
    }

    @Override
    public void initPresenter() {
        categoryPagerPresenter = CategoryPagerPresenterImpl.getInstance();
        categoryPagerPresenter.registerCallback(this);
    }

    @Override
    public void loadData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        int materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        LogUtils.d(HomePagerFragment.class, "materialId -> " + materialId);
        LogUtils.d(HomePagerFragment.class, "categoryPagerPresenter -> " + categoryPagerPresenter);
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
    }

    @Override
    public void onLoading(int categoryId) {

    }

    @Override
    public void onError(int categoryId) {

    }

    @Override
    public void onEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreError(int categoryId) {

    }

    @Override
    public void onLoaderMoreEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContent.DataBean.ListBean> list) {

    }
}
