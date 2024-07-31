package vip.dengwj.feitian_union.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    public static RetrofitManager retrofitManager = new RetrofitManager();
    private final Retrofit retrofit;
    private final Retrofit retrofit2;

    public static RetrofitManager getInstance() {
        return retrofitManager;
    }

    private RetrofitManager() {
        // 创建OkHttpClient实例
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        okHttpClientBuilder.connectTimeout(10000, TimeUnit.MILLISECONDS);

        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        retrofit2 = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit getRetrofit2() {
        return retrofit2;
    }
}
