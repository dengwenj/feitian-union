package vip.dengwj.feitian_union.presenter;

import vip.dengwj.feitian_union.base.BasePresenter;
import vip.dengwj.feitian_union.view.TicketCallback;

public interface TicketPresenter extends BasePresenter<TicketCallback> {
    void getTicket(String title, String url, String cover);
}
