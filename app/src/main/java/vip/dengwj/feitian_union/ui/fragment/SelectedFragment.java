package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;
import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.SelectedCallback;

public class SelectedFragment extends BaseFragment implements SelectedCallback {

    private SelectedPagePresenter selectedPagePresenter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_selected;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);

        selectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        selectedPagePresenter.registerCallback(this);
        selectedPagePresenter.getCategory();
    }

    @Override
    public void categoryLoaded(List<SelectedCategory.DataBean> categoryList) {
        LogUtils.d(SelectedFragment.class, "categoryList -> " + categoryList);
    }

    @Override
    public void categoryContentLoaded(List<HomePagerContent.DataBean.ListBean> list) {

    }

    @Override
    public void onNetworkError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}
