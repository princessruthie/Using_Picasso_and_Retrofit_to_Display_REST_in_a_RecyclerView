package com.ruthiefloats.asynctask;

import com.ruthiefloats.asynctask.model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;


/**
 * Created by fieldsru on 6/29/16.
 */
public interface FlowersAPI {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);

    @GET("/feeds/flowers.json")
    public Call<List<Flower>> getFeed2();
}
