package vip.dengwj.feitian_union.model;

import retrofit2.Call;
import retrofit2.http.GET;
import vip.dengwj.feitian_union.model.domain.Categories;

public interface API {
    @GET("discovery/categories")
    Call<Categories> getCategories();
}
