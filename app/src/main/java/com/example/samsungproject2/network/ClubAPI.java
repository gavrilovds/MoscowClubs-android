package com.example.samsungproject2.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClubAPI {

    @GET("/clubs")
    Call<JsonObject> getClub(@Query("name") String name);

    @GET("/clubs/get-all")
    Call<JsonArray> getAllClubs();

}
