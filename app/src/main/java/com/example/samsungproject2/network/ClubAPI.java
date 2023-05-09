package com.example.samsungproject2.network;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClubAPI {

    @GET("/clubs")
    Call<JsonObject> getClub(@Query("name") String name);

    @GET("/clubs/get-all")
    Call<JsonArray> getAllClubs();

    @POST("/clubs")
    Call<ResponseBody> addClub(@Body Club club);

    @POST("/clubs/add-event")
    Call<ResponseBody> addEvent(@Query("clubName") String clubName, @Body Event entity);
}
