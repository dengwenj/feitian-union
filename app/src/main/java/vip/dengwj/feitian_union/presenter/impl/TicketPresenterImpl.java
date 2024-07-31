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
    private TicketCallback ticketCallback;

    enum LoadState {
        LOADING, SUCCESS, ERROR, NONE
    }

    private LoadState state = LoadState.NONE;
    private String cover;
    private Ticket ticket;

    // 获取淘口令
    @Override
    public void getTicket(String title, String url, String cover) {
        handleLoading();

        API api = RetrofitManager.getInstance().getRetrofit2().create(API.class);
        api.getTicket(new TicketParams(url, title)).enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.code() == HTTP_OK) {
                    Ticket result = response.body();
                    if (result == null) {
                        handleError();
                    } else {
                        TicketPresenterImpl.this.cover = cover;
                        TicketPresenterImpl.this.ticket = result;
                        handleSuccess();
                    }
                } else {
                    handleError();
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                handleError();
                LogUtils.d(TicketPresenterImpl.class, "t -> " + t.getMessage());
            }
        });
    }

    private void handleLoading() {
        if (ticketCallback == null) return;

        state = LoadState.LOADING;
        ticketCallback.onLoading();
    }

    private void handleSuccess() {
        if (ticketCallback == null) return;

        state = LoadState.SUCCESS;
        ticketCallback.onTicketLoaded(cover, ticket);
        state = LoadState.NONE;
    }

    private void handleError() {
        if (ticketCallback == null) return;

        state = LoadState.ERROR;
        ticketCallback.onNetworkError();
    }

    @Override
    public void registerCallback(TicketCallback callback) {
        ticketCallback = callback;
        // 可能 getTicket 比跳转页面快
        if (state != LoadState.NONE) {
            // 状态已经变了 更新 UI
            if (state == LoadState.SUCCESS) {
                handleSuccess();
            } else if (state == LoadState.ERROR) {
                handleError();
            } else if (state == LoadState.LOADING) {
                handleLoading();
            }
        }
    }

    @Override
    public void unregisterCallback(TicketCallback callback) {
        ticketCallback = null;
    }
}
