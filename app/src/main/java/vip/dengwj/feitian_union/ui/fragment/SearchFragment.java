package vip.dengwj.feitian_union.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentSearchBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.view.SearchCallback;

public class SearchFragment extends BaseFragment implements SearchCallback {

    private SearchPresenter searchPresenter;
    private FragmentSearchBinding searchBinding;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_search;
    }

    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_search_fragment_layout, container, false);
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);

        searchBinding = FragmentSearchBinding.bind(rootView);
    }

    @Override
    public void initPresenter() {
        searchPresenter = PresenterManager.getInstance().getSearchPresenter();
        searchPresenter.registerCallback(this);
        // 获取推荐词
        searchPresenter.getRecommendWords();
        // 搜索
        searchPresenter.doSearch("女装", false);
        // 历史记录
        searchPresenter.getHistories();
    }

    @Override
    public void initListener() {
        super.initListener();

        searchBinding.historyTextFlow.setOnItemClickListener(this::handleClickHistory);
        searchBinding.recommendTextFlow.setOnItemClickListener(this::handleClickRecommend);
        searchBinding.imgDel.setOnClickListener(this::handleDelHistory);
    }

    /**
     * 点击热搜推荐
     * @param word
     */
    private void handleClickRecommend(String word) {
        LogUtils.d(SearchFragment.class, "handleClickRecommend -> " + word);
    }

    /**
     * 删除历史
     * @param view
     */
    private void handleDelHistory(View view) {
        LogUtils.d(SearchFragment.class, "删除了历史");
    }

    /**
     * 点击 历史记录
     * @param word
     */
    private void handleClickHistory(String word) {
        LogUtils.d(SearchFragment.class, "handleClickHistory -> " + word);
    }

    @Override
    public void release() {
        searchPresenter.unregisterCallback(this);
    }

    @Override
    public void onHistoryLoaded(List<String> historyList) {
        if (historyList.isEmpty()) {
            searchBinding.history.setVisibility(View.GONE);
            return;
        }
        searchBinding.history.setVisibility(View.VISIBLE);
        searchBinding.historyTextFlow.setTextList(historyList);
    }

    @Override
    public void onHistoryDeleted() {

    }

    @Override
    public void onSearchSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(SearchFragment.class, "list -> " + list);
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
        if (recommendWords.isEmpty()) {
            searchBinding.recommend.setVisibility(View.GONE);
            return;
        }
        searchBinding.recommend.setVisibility(View.VISIBLE);
        searchBinding.recommendTextFlow.setTextList(recommendWords);
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
