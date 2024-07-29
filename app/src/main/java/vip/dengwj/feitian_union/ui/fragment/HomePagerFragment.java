package vip.dengwj.feitian_union.ui.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

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
        setupState(State.SUCCESS);
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

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContent.DataBean.ListBean> list) {

    }

    // 轮播图请求回来的数据
    @Override
    public void onLooperListLoaded(List<LoopList.DataBean> list) {
        looperAdapter.setData(list);

        fragmentHomePagerBinding.looper.setCurrentItem(Integer.MAX_VALUE / 2);

        Context context = getContext();
        fragmentHomePagerBinding.viewPagerPoint.removeAllViews();
        int size = SizeUtils.dip2px(context, 8);
        Drawable selectDrawable = AppCompatResources.getDrawable(context, R.drawable.looper_point);
        GradientDrawable normalDrawable = (GradientDrawable) AppCompatResources.getDrawable(context, R.drawable.looper_point);
        normalDrawable.setColor(context.getColor(R.color.white));

        // 更新 UI，指示点
        for (int i = 0; i < list.size(); i++) {
            View view = new View(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            layoutParams.leftMargin = size;
            view.setLayoutParams(layoutParams);
            if (i == 0) {
                view.setBackground(selectDrawable);
            } else {
                view.setBackground(normalDrawable);
            }
            fragmentHomePagerBinding.viewPagerPoint.addView(view);
        }
    }
}
