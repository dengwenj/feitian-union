package vip.dengwj.feitian_union.ui.fragment;

import android.os.Bundle;
import android.view.View;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.utils.Constants;
import vip.dengwj.feitian_union.utils.LogUtils;

public class HomePagerFragment extends BaseFragment {
    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID, category.getId());
        homePagerFragment.setArguments(bundle);

        return homePagerFragment;
    }

    @Override
    public int loadRootViewId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
    }

    @Override
    public void loadData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        int materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);

        LogUtils.d(HomePagerFragment.class, "title ->" + title);
        LogUtils.d(HomePagerFragment.class, "materialId ->" + materialId);
    }
}
