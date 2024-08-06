package vip.dengwj.feitian_union.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.ui.custom.TextFlowView;
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
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.base_search_fragment_layout, container, false);
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);

        TextFlowView textFlowView = rootView.findViewById(R.id.text_flow_view);
        LogUtils.d(HomeFragment.class, "textFlowView ->" + textFlowView);
        List<String> list = new ArrayList<>();
        list.add("李雷");
        list.add("朴睦");
        list.add("今天开心吗？今天不是很开心因为我的美股大跌");
        list.add("韩梅梅");
        list.add("韩梅梅");
        list.add("韩梅梅");
        list.add("韩梅梅");
        list.add("韩梅梅");
        textFlowView.setTextList(list);

        textFlowView.setOnItemClickListener(new TextFlowView.OnItemClickListener() {
            @Override
            public void onItemClick(String word) {
                LogUtils.d(SearchFragment.class, "word -> " + word);
            }
        });
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
    public void release() {
        searchPresenter.unregisterCallback(this);
    }

    @Override
    public void onHistoryLoaded(List<String> historyList) {
        LogUtils.d(SearchFragment.class, "historyList ->" + historyList);
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
