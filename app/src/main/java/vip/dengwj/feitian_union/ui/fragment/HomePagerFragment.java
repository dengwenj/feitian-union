package vip.dengwj.feitian_union.ui.fragment;

import android.view.View;

import vip.dengwj.feitian_union.R;
import vip.dengwj.feitian_union.base.BaseFragment;

public class HomePagerFragment extends BaseFragment {
    @Override
    public int loadRootViewId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    public void initView(View rootView) {
        setupState(State.SUCCESS);
    }
}
