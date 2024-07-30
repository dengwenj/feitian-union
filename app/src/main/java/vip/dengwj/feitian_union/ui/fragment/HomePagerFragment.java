package vip.dengwj.feitian_union.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.databinding.FragmentHomePagerBinding;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.LoopList;
import vip.dengwj.feitian_union.presenter.CategoryPagerPresenter;
import vip.dengwj.feitian_union.presenter.impl.CategoryPagerPresenterImpl;
import vip.dengwj.feitian_union.ui.adapter.HomePagerItemAdapter;
import vip.dengwj.feitian_union.ui.adapter.LooperAdapter;
import vip.dengwj.feitian_union.utils.Constants;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.SizeUtils;
import vip.dengwj.feitian_union.utils.ToastUtils;
import vip.dengwj.feitian_union.view.CategoryPagerCallback;

public class HomePagerFragment extends BaseFragment implements CategoryPagerCallback {

    private CategoryPagerPresenter categoryPagerPresenter;
    private int materialId;
    private FragmentHomePagerBinding fragmentHomePagerBinding;
    private HomePagerItemAdapter pagerItemAdapter;
    private LooperAdapter looperAdapter;

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

        // 轮播图
        looperAdapter = new LooperAdapter();
        fragmentHomePagerBinding.looper.setAdapter(looperAdapter);

        // item 是内容
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentHomePagerBinding.homePageItem.setLayoutManager(layoutManager);
        pagerItemAdapter = new HomePagerItemAdapter();
        fragmentHomePagerBinding.homePageItem.setAdapter(pagerItemAdapter);
        // 设置 Refresh 相关属性
        // 下拉刷新关闭
        fragmentHomePagerBinding.refresh.setEnableRefresh(false);
        // 上拉加载更新
        fragmentHomePagerBinding.refresh.setEnableLoadmore(true);
        setupState(State.SUCCESS);
    }

    // 看家页面
    @Override
    public void onResume() {
        super.onResume();
        fragmentHomePagerBinding.looper.startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentHomePagerBinding.looper.stopLoop();
    }

    @Override
    public void initListener() {
        super.initListener();

        // 监听全局布局改变
        fragmentHomePagerBinding.homePagerParent
                .getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int headerHeight = fragmentHomePagerBinding.header.getMeasuredHeight();
                        fragmentHomePagerBinding.ftNestedScrollView.setHeaderHeight(headerHeight);

                        int measuredHeight = fragmentHomePagerBinding.homePagerParent.getMeasuredHeight();
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) fragmentHomePagerBinding.homePageItem.getLayoutParams();
                        if (measuredHeight != 0) {
                            layoutParams.height = measuredHeight;
                            fragmentHomePagerBinding.homePageItem.setLayoutParams(layoutParams);
                            // 销毁事件
                            fragmentHomePagerBinding.homePagerParent
                                    .getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                        }
                    }
                });

        fragmentHomePagerBinding.looper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int dataSize = looperAdapter.getDataSize();
                if (dataSize == 0) {
                    return;
                }
                int realPosition = position % dataSize;
                updatePoint(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragmentHomePagerBinding.refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            // 上拉加载更多事件
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(HomePagerFragment.class, "上拉加载更多...");
                // 加载更多
                categoryPagerPresenter.loaderMore(materialId);
            }
            // 下拉刷新
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
            }
        });
    }

    private void updatePoint(int realPosition) {
        int childCount = fragmentHomePagerBinding.viewPagerPoint.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View point = fragmentHomePagerBinding.viewPagerPoint.getChildAt(i);
            if (realPosition == i) {
                point.setBackgroundResource(R.drawable.looper_point);
            } else {
                point.setBackgroundResource(R.drawable.edit_bg);
            }
        }
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
        categoryPagerPresenter.getContentByCategoryId(materialId);

        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        fragmentHomePagerBinding.title.showTitle.setText(title);
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
    public void onLoaderMoreError() {
        ToastUtils.showToast("网络错误，请稍后重试");
        fragmentHomePagerBinding.refresh.finishLoadmore();
    }

    @Override
    public void onLoaderMoreEmpty() {
        ToastUtils.showToast("没有更多数据了");
        fragmentHomePagerBinding.refresh.finishLoadmore();
    }

    // 加载更多数据
    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {
        LogUtils.d(HomePagerFragment.class, "list ==> " + list);
        pagerItemAdapter.addData(list);
        ToastUtils.showToast("加载成功");
        // 关闭加载更多动画
        fragmentHomePagerBinding.refresh.finishLoadmore();
    }

    // 轮播图请求回来的数据
    @Override
    public void onLooperListLoaded(List<LoopList.DataBean> list) {
        looperAdapter.setData(list);

        // 中间点 % 数据的 size 不一定为 0，所以显示的就不是第一个
        int dx = (Integer.MAX_VALUE / 2) % list.size();
        int realPosition = (Integer.MAX_VALUE / 2) - dx;
        // 设置到中间点
        fragmentHomePagerBinding.looper.setCurrentItem(realPosition);

        Context context = getContext();
        fragmentHomePagerBinding.viewPagerPoint.removeAllViews();
        int size = SizeUtils.dip2px(context, 8);

        // 更新 UI，指示点
        for (int i = 0; i < list.size(); i++) {
            View view = new View(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = size;
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setBackgroundResource(R.drawable.looper_point);
            } else {
                view.setBackgroundResource(R.drawable.edit_bg);
            }
            fragmentHomePagerBinding.viewPagerPoint.addView(view);
        }
    }
}
