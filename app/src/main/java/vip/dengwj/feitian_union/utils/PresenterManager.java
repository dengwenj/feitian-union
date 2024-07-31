package vip.dengwj.feitian_union.utils;

import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();

    private final TicketPresenterImpl ticketPresenter;

    public TicketPresenter getTicketPresenter() {
        return ticketPresenter;
    }

    private PresenterManager() {
        ticketPresenter = new TicketPresenterImpl();
    }

    public static PresenterManager getInstance() {
        return ourInstance;
    }
}
