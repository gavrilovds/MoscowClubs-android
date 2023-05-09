package com.example.samsungproject2.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentAPI {
    @POST("/comments")
    Call<ResponseBody> addComment(@Query("text") String text, @Query("token") String token, @Query("clubName") String clubName);

    @DELETE("/comments")
    Call<ResponseBody> deleteComment(@Query("id") Long id);
}
