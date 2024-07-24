package vip.dengwj.feitian_union.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    public static RetrofitManager retrofitManager = new RetrofitManager();
    private final Retrofit retrofit;

    public static RetrofitManager getInstance() {
        return retrofitManager;
    }

    private RetrofitManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
