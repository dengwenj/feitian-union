package vip.dengwj.feitian_union.utils;

import vip.dengwj.feitian_union.presenter.RedPacketPresenter;
import vip.dengwj.feitian_union.presenter.SearchPresenter;
import vip.dengwj.feitian_union.presenter.SelectedPagePresenter;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.presenter.impl.RedPacketPresenterImpl;
import vip.dengwj.feitian_union.presenter.impl.SearchPresenterImpl;
import vip.dengwj.feitian_union.presenter.impl.SelectedPagePresenterImpl;
import vip.dengwj.feitian_union.presenter.impl.TicketPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();

    private final TicketPresenterImpl ticketPresenter;
    private final SelectedPagePresenterImpl selectedPagePresenter;
    private final RedPacketPresenter redPacketPresenter;
    private final SearchPresenter searchPresenter;

    private PresenterManager() {
        ticketPresenter = new TicketPresenterImpl();
        selectedPagePresenter = new SelectedPagePresenterImpl();
        redPacketPresenter = new RedPacketPresenterImpl();
        searchPresenter = new SearchPresenterImpl();
    }

    public TicketPresenter getTicketPresenter() {
        return ticketPresenter;
    }

    public SelectedPagePresenter getSelectedPagePresenter() {
        return selectedPagePresenter;
    }

    public RedPacketPresenter getRedPacketPresenter() {
        return redPacketPresenter;
    }

    public SearchPresenter getSearchPresenter() {
        return searchPresenter;
    }

    public static PresenterManager getInstance() {
        return ourInstance;
    }
}
