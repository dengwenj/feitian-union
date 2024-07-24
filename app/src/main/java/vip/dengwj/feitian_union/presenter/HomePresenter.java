package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.view.HomeCallback;

public interface HomePresenter {
    void getCategories();

    void registerCallback(HomeCallback homeCallback);

    void unregisterCallback(HomeCallback homeCallback);
}
