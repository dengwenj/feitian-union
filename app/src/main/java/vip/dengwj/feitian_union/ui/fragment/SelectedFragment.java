package vip.dengwj.feitian_union.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentSelectedBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;
import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.ui.activity.TicketActivity;
import vip.dengwj.feitian_union.ui.adapter.SelectedLeftAdapter;
import vip.dengwj.feitian_union.ui.adapter.SelectedRightAdapter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.SelectedCallback;

public class SelectedFragment extends BaseFragment implements SelectedCallback {

    private SelectedPagePresenter selectedPagePresenter;
    private FragmentSelectedBinding selectedBinding;
    private SelectedLeftAdapter selectedLeftAdapter;
    private SelectedRightAdapter selectedRightAdapter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_selected;
    }

    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_selected_fragment_layout, container, false);
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
        selectedBinding = FragmentSelectedBinding.bind(rootView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        selectedBinding.leftCategory.setLayoutManager(layoutManager);
        selectedLeftAdapter = new SelectedLeftAdapter();
        selectedBinding.leftCategory.setAdapter(selectedLeftAdapter);

        // 右侧
        selectedBinding.rightCategoryContent.setLayoutManager(new LinearLayoutManager(getContext()));
        selectedRightAdapter = new SelectedRightAdapter();
        selectedBinding.rightCategoryContent.setAdapter(selectedRightAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        selectedLeftAdapter.setOnCategoryLeftClickListener(this::handleClickLeftCategory);
        selectedRightAdapter.setOnLygmClickListener(this::handleClickLqgm);
    }

    private void handleClickLqgm(String title, String url, String cover) {
        TicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);
        startActivity(new Intent(getContext(), TicketActivity.class));
    }

    private void handleClickLeftCategory(int categoryId) {
        selectedPagePresenter.getCategoryContent(categoryId);
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
        setupState(State.SUCCESS);
        selectedLeftAdapter.setData(categoryList);
        selectedPagePresenter.getCategoryContent(categoryList.get(0).getMaterialId());
    }

    @Override
    public void categoryContentLoaded(List<HomePagerContent.DataBean.ListBean> list) {
        selectedRightAdapter.setData(list);
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
    public void resetNetworkError() {
        selectedPagePresenter.getCategory();
    }
}
