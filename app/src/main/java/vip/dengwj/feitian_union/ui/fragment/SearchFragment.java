package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.SearchCallback;

public class SearchFragment extends BaseFragment implements SearchCallback {

    private SearchPresenter searchPresenter;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_search;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
    }

    @Override
    public void initPresenter() {
        searchPresenter = PresenterManager.getInstance().getSearchPresenter();
        searchPresenter.registerCallback(this);
        searchPresenter.getRecommendWords();
    }

    @Override
    public void release() {
        searchPresenter.unregisterCallback(this);
    }

    @Override
    public void onHistoryLoaded(List<String> historyList) {

    }

    @Override
    public void onHistoryDeleted() {

    }

    @Override
    public void onSearchSuccess(List<HomePagerContent.DataBean.ListBean> list) {

    }

    @Override
    public void onMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {

    }

    @Override
    public void onMoreLoadedError() {

    }

    @Override
    public void onMoreLoadedEmpty() {

    }

    @Override
    public void onRecommendWordsLoaded(List<String> recommendWords) {
        LogUtils.d(SearchFragment.class, "recommendWords ->" + recommendWords);
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
