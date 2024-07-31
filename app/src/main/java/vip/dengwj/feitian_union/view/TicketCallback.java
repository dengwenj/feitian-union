package vip.dengwj.feitian_union.view;

import vip.dengwj.feitian_union.base.BaseCallback;
import vip.dengwj.feitian_union.model.domain.Ticket;

public interface TicketCallback extends BaseCallback {
    void onTicketLoaded(String cover, Ticket ticketResult);
}
