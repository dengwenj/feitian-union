package vip.dengwj.feitian_union.utils;

import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.presenter.impl.SelectedPagePresenterImpl;
import vip.dengwj.feitian_union.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();

    private final TicketPresenterImpl ticketPresenter;
    private final SelectedPagePresenterImpl selectedPagePresenter;

    private PresenterManager() {
        ticketPresenter = new TicketPresenterImpl();
        selectedPagePresenter = new SelectedPagePresenterImpl();
    }

    public TicketPresenter getTicketPresenter() {
        return ticketPresenter;
    }

    public SelectedPagePresenter getSelectedPagePresenter() {
        return selectedPagePresenter;
    }

    public static PresenterManager getInstance() {
        return ourInstance;
    }
}
