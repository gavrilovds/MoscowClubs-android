package com.example.samsungproject2.viewmodel.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.network.CommentAPI;
import com.example.samsungproject2.network.UserAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileViewModel extends ViewModel {
    private UserAPI userAPI;
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();
    private String server;
    private CommentAPI commentAPI;

    public MutableLiveData<User> getMutableLiveData() {
        return mutableLiveData;
    }

    public UserProfileViewModel() {
        init();
    }

    private void init(){
        server = "http://10.0.2.2:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);
        commentAPI = retrofit.create(CommentAPI.class);
    }

    public void getUser(String token){
        Call<JsonObject> call = userAPI.getUser(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                User user = new Gson().fromJson(response.body().getAsJsonObject(), User.class);
                mutableLiveData.postValue(user);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void deleteComment(Long id, String token){
        Call<ResponseBody> call = commentAPI.deleteComment(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getUser(token);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
