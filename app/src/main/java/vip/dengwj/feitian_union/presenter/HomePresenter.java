package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.HomeCallback;

public interface HomePresenter extends BasePresenter<HomeCallback> {
    void getCategories();
}
