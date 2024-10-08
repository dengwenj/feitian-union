package vip.dengwj.feitian_union.model;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.LoopList;
import vip.dengwj.feitian_union.model.domain.Recommend;
import vip.dengwj.feitian_union.model.domain.SelectedCategory;
import vip.dengwj.feitian_union.model.domain.Ticket;
import vip.dengwj.feitian_union.model.domain.TicketParams;

public interface API {
    @GET("shop/category/list")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);

    @GET("shop/banner")
    Call<LoopList> getLoopList(@Query("count") int count);

    @POST("tpwd")
    Call<Ticket> getTicket(@Body TicketParams params);

    @GET("shop/material/list")
    Call<SelectedCategory> getCategory();

    @GET
    Call<HomePagerContent> getYouLikeContent(@Url String url);

    @GET("shop/search-word")
    Call<Recommend> getRecommend();

    @GET
    Call<HomePagerContent> getSearchList(@Url String url);
}
