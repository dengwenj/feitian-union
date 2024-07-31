package vip.dengwj.feitian_union.presenter.impl;

import static java.net.HttpURLConnection.HTTP_OK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vip.dengwj.feitian_union.model.API;
import vip.dengwj.feitian_union.model.domain.Ticket;
import vip.dengwj.feitian_union.model.domain.TicketParams;
import vip.dengwj.feitian_union.presenter.TicketPresenter;
import vip.dengwj.feitian_union.utils.LogUtils;
import vip.dengwj.feitian_union.utils.RetrofitManager;
import vip.dengwj.feitian_union.view.TicketCallback;

public class TicketPresenterImpl implements TicketPresenter {
    @Override
    public void getTicket(String title, String url, String cover) {
        API api = RetrofitManager.getInstance().getRetrofit2().create(API.class);
        api.getTicket(new TicketParams(url, title)).enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.code() == HTTP_OK) {
                    Ticket ticket = response.body();
                    LogUtils.d(TicketPresenterImpl.class, "body -> " + ticket);
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                LogUtils.d(TicketPresenterImpl.class, "t -> " + t.getMessage());
            }
        });
    }

    @Override
    public void registerCallback(TicketCallback callback) {

    }

    @Override
    public void unregisterCallback(TicketCallback callback) {

    }
}
