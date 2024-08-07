package vip.dengwj.feitian_union.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentSearchBinding;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.ui.activity.IMainActivity;
import vip.dengwj.feitian_union.ui.activity.TicketActivity;
import vip.dengwj.feitian_union.ui.adapter.HomePagerItemAdapter;
import vip.dengwj.feitian_union.utils.HideInputUtil;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.PresenterManager;
import vip.dengwj.feitian_union.utils.ToastUtils;
import vip.dengwj.feitian_union.view.SearchCallback;

public class SearchFragment extends BaseFragment implements SearchCallback {

    private SearchPresenter searchPresenter;
    private FragmentSearchBinding searchBinding;
    private HomePagerItemAdapter homePagerItemAdapter;
    private View baseSearchLayout;
    private TextView editRightBtn;

    private String keyWords;
    private EditText editText;

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_search;
    }

    @Override
    public View loadRootView(LayoutInflater inflater, ViewGroup container) {
        baseSearchLayout = inflater.inflate(R.layout.base_search_fragment_layout, container, false);
        return baseSearchLayout;
    }

    // 页面启动
    @Override
    public void onStart() {
        super.onStart();
        searchShowKeyboard(false);
    }

    /**
     * 显示和隐藏式触发
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        searchShowKeyboard(hidden);
    }

    /**
     * 其他地方点击搜索进入该页面
     */
    private void searchShowKeyboard(boolean hidden) {
        FragmentActivity activity = getActivity();
        if (activity instanceof IMainActivity) {
            IMainActivity mainActivity = (IMainActivity) activity;
            // 不是点击搜索过来的就不执行了
            if (!mainActivity.getIsSwitch2Search()) {
                return;
            }

            // 显示时焦点聚焦
            if (!hidden) {
                editText.requestFocus();
                // 弹起键盘
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }

            mainActivity.setIsSwitch2Search(false);
        }
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);

        searchBinding = FragmentSearchBinding.bind(rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        searchBinding.searchRecyclerView.setLayoutManager(layoutManager);
        homePagerItemAdapter = new HomePagerItemAdapter();
        searchBinding.searchRecyclerView.setAdapter(homePagerItemAdapter);
        searchBinding.searchRefresh.setEnableRefresh(false);

        // 顶部的搜索框
        editRightBtn = baseSearchLayout.findViewById(R.id.edit_right_btn);
        editRightBtn.setOnClickListener(this::handleEditRightBtn);
        editText = baseSearchLayout.findViewById(R.id.search_edit);
        // 搜索框变化的事件
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyWords = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // 点击键盘的搜索
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    clickWordSearch(keyWords);
                }
                return false;
            }
        });
    }

    /**
     * 点击搜索或取消
     * @param view
     */
    private void handleEditRightBtn(View view) {
        // 搜索
        if (editRightBtn.getText().equals("搜索")) {
            clickWordSearch(keyWords);
        } else {
            // 取消
            editRightBtn.setText("搜索");
            editText.setText("");
            searchBinding.history.setVisibility(View.VISIBLE);
            searchBinding.recommend.setVisibility(View.VISIBLE);
            searchBinding.searchRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initPresenter() {
        searchPresenter = PresenterManager.getInstance().getSearchPresenter();
        searchPresenter.registerCallback(this);
        // 获取推荐词
        searchPresenter.getRecommendWords();
        // 历史记录
        searchPresenter.getHistories();
    }

    @Override
    public void initListener() {
        super.initListener();

        searchBinding.historyTextFlow.setOnItemClickListener(this::handleClickHistory);
        searchBinding.recommendTextFlow.setOnItemClickListener(this::handleClickRecommend);
        searchBinding.imgDel.setOnClickListener(this::handleDelHistory);
        homePagerItemAdapter.setOnListItemListener(this::handleItemList);
        searchBinding.searchRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                searchPresenter.loaderMore();
            }
        });
    }

    /**
     * 点击 item
     */
    private void handleItemList(HomePagerContent.DataBean.ListBean item) {
        handleTicket(item.getTitle(), item.getCouponShareUrl(), item.getCover());
    }

    private void handleTicket(String title, String url, String cover) {
        TicketPresenter ticketPresenter = PresenterManager.getInstance().getTicketPresenter();
        ticketPresenter.getTicket(title, url, cover);

        Intent intent = new Intent(getContext(), TicketActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 点击热搜推荐
     * @param word
     */
    private void handleClickRecommend(String word) {
        clickWordSearch(word);
    }

    /**
     * 点击了搜索
     */
    private void clickWordSearch(String word) {
        if (keyWords == null || keyWords.trim().isEmpty()) {
            ToastUtils.showToast("请输入内容");
            return;
        }

        HideInputUtil.hideOneInputMethod(getActivity(), editText);
        keyWords = word;
        editRightBtn.setText("取消");
        editText.setText(keyWords);
        setupState(State.LOADING);
        searchPresenter.doSearch(word, false);
        // 更新搜索历史
        searchPresenter.getHistories();
        editText.clearFocus();
    }

    /**
     * 删除历史
     * @param view
     */
    private void handleDelHistory(View view) {
        searchPresenter.deleteHistory();
    }

    /**
     * 点击 历史记录
     * @param word
     */
    private void handleClickHistory(String word) {
        clickWordSearch(word);
    }

    @Override
    public void release() {
        searchPresenter.unregisterCallback(this);
    }

    @Override
    public void onHistoryLoaded(List<String> historyList) {
        if (historyList == null || historyList.isEmpty()) {
            searchBinding.history.setVisibility(View.GONE);
            return;
        }
        searchBinding.history.setVisibility(View.VISIBLE);
        searchBinding.historyTextFlow.setTextList(historyList);
    }

    @Override
    public void onHistoryDeleted() {
        searchPresenter.getHistories();
    }

    /**
     * 搜索成功的回调
     * @param list
     */
    @Override
    public void onSearchSuccess(List<HomePagerContent.DataBean.ListBean> list) {
        setupState(State.SUCCESS);
        searchBinding.history.setVisibility(View.GONE);
        searchBinding.recommend.setVisibility(View.GONE);
        searchBinding.searchRecyclerView.setVisibility(View.VISIBLE);
        homePagerItemAdapter.setData(list);
    }

    @Override
    public void onMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {
        ToastUtils.showToast("加载成功");
        searchBinding.searchRefresh.finishLoadmore();
        homePagerItemAdapter.addData(list);
    }

    @Override
    public void onMoreLoadedError() {
        ToastUtils.showToast("系统错误，请稍后重试");
    }

    @Override
    public void onMoreLoadedEmpty() {
        ToastUtils.showToast("数据加载完啦");
    }

    @Override
    public void onRecommendWordsLoaded(List<String> recommendWords) {
        setupState(State.SUCCESS);
        if (recommendWords == null || recommendWords.isEmpty()) {
            searchBinding.recommend.setVisibility(View.GONE);
            return;
        }
        searchBinding.recommend.setVisibility(View.VISIBLE);
        searchBinding.recommendTextFlow.setTextList(recommendWords);
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
}
