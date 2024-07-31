package vip.dengwj.feitian_union.presenter.impl;

import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.view.TicketCallback;

public class TicketPresenterImpl implements TicketPresenter {
    @Override
    public void getTicket(String title, String url, String cover) {
        LogUtils.d(TicketPresenterImpl.class, "title url cover" + title + url + cover);
    }

    @Override
    public void registerCallback(TicketCallback callback) {

    }

    @Override
    public void unregisterCallback(TicketCallback callback) {

    }
}
