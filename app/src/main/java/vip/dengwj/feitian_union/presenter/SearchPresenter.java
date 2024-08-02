package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.SearchCallback;

public interface SearchPresenter extends BasePresenter<SearchCallback> {
    /**
     * 获取搜索历史内容
     */
    void getHistories();

    /**
     * 删除搜索历史
     */
    void deleteHistory();

    /**
     * 搜索
     */
    void doSearch(String keyword);

    /**
     * 重新搜索
     */
    void research();

    /**
     * 获取更多的搜索结果
     */
    void loaderMore();

    /**
     * 获取推荐词
     */
    void getRecommendWords();
}
