package com.example.samsungproject2.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClubImagesAPI {
    @POST("/clubs/images")
    Call<ResponseBody> addClubImage(@Query("clubName") String clubName, @Query("url") String imageUrl);
}
