package com.ruthiefloats.asynctask;

import com.ruthiefloats.asynctask.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by fieldsru on 6/29/16.
 */
public interface FlowersAPI {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);
}
