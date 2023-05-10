package com.example.samsungproject2.viewmodel.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.network.ClubAPI;
import com.example.samsungproject2.network.UserAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileSettingsViewModel extends ViewModel {
    private UserAPI userAPI;
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private String server;
    private ClubAPI clubAPI;

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public ProfileSettingsViewModel() {
        init();
    }

    private void init() {
        server = "http://158.160.29.103:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);
        clubAPI = retrofit.create(ClubAPI.class);
    }

    public void getUser(String token) {
        Call<JsonObject> call = userAPI.getUser(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                User user = new Gson().fromJson(response.body().getAsJsonObject(), User.class);
                userMutableLiveData.postValue(user);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void addClub(Club club) {
        Call<ResponseBody> call = clubAPI.addClub(club);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void setAdmin(String token, String clubName) {
        Call<ResponseBody> call = userAPI.setAdmin(token, clubName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void changeName(String token, String newName) {
        Call<ResponseBody> call = userAPI.changeName(token, newName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
