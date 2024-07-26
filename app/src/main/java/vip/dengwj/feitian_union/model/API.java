package vip.dengwj.feitian_union.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import vip.dengwj.feitian_union.model.domain.Categories;
import vip.dengwj.feitian_union.model.domain.HomePagerContent;
import vip.dengwj.feitian_union.model.domain.LoopList;

public interface API {
    @GET("shop/category/list")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerContent> getHomePagerContent(@Url String url);

    @GET("shop/banner")
    Call<LoopList> getLoopList(@Query("count") int count);
}
