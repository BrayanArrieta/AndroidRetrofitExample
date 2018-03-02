package com.example.arrieta.androidretrofitexample.Interfaces;

import com.example.arrieta.androidretrofitexample.Models.Hero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Arrieta on 1/3/2018.
 */

public interface Heroes {
    @GET("/heroes")
    Call<List<Hero>> get(@Query("id") Integer id);
    @POST("/heroes")
    Call<Hero> post(@Body Hero hero);
    @PUT("/heroes/{id}")
    Call<Hero> put(@Path("id") Integer id, @Body Hero hero);
    @DELETE("/heroes/{id}")
    Call<Hero> delete(@Path("id") Integer id );

}
