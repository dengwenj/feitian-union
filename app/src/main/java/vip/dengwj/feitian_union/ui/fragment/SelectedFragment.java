package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentSelectedBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;
import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.ui.adapter.SelectedLeftAdapter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.SelectedCallback;

public class SelectedFragment extends BaseFragment implements SelectedCallback {

    private SelectedPagePresenter selectedPagePresenter;
    private FragmentSelectedBinding selectedBinding;
    private SelectedLeftAdapter selectedLeftAdapter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_selected;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
        selectedBinding = FragmentSelectedBinding.bind(rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        selectedBinding.leftCategory.setLayoutManager(layoutManager);
        selectedLeftAdapter = new SelectedLeftAdapter();
        selectedBinding.leftCategory.setAdapter(selectedLeftAdapter);
    }

    @Override
    public void initListener() {
        selectedLeftAdapter.setOnCategoryLeftClickListener(new SelectedLeftAdapter.OnCategoryLeftClickListener() {
            @Override
            public void onCategoryLeftClick() {
                LogUtils.d(SelectedFragment.class, "SelectedFragment");
            }
        });
    }

    @Override
    public void release() {
        super.release();
        selectedPagePresenter.unregisterCallback(this);
    }

    @Override
    public void initPresenter() {
        selectedPagePresenter = PresenterManager.getInstance().getSelectedPagePresenter();
        selectedPagePresenter.registerCallback(this);
        selectedPagePresenter.getCategory();
    }

    @Override
    public void categoryLoaded(List<SelectedCategory.DataBean> categoryList) {
        selectedLeftAdapter.setData(categoryList);
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
