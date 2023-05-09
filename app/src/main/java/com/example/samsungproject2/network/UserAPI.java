package com.example.samsungproject2.network;

import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.model.user.UserForRegistration;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {
    @POST("/users")
    Call<ResponseBody> registration(@Body UserForRegistration user);

    @GET("/users")
    Call<JsonObject> getUser(@Query("token") String token);

    @POST("/users/favourites")
    Call<ResponseBody> addClubToFavourites(@Query("token") String token, @Query("clubName") String clubName);

    @DELETE("/users/favourites")
    Call<ResponseBody> deleteClubFromFavourites(@Query("token") String token, @Query("clubName") String clubName);

    @POST("/users/sign-in")
    Call<ResponseBody> signIn(@Query("email") String email, @Query("password") String password);

    @POST("/users/set-admin")
    Call<ResponseBody> setAdmin(@Query("token") String token, @Query("clubName") String clubName);

    @POST("/users/change-name")
    Call<ResponseBody> changeName(@Query("token") String token, @Query("newName") String newName);
}
