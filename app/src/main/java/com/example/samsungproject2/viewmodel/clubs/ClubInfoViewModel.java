package com.example.samsungproject2.viewmodel.clubs;

import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.model.Event;
import com.example.samsungproject2.model.user.User;
import com.example.samsungproject2.network.ClubAPI;
import com.example.samsungproject2.network.ClubImagesAPI;
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

public class ClubInfoViewModel extends ViewModel {
    private ClubAPI clubAPI;
    private UserAPI userAPI;
    private ClubImagesAPI clubImagesAPI;
    private CommentAPI commentAPI;
    private String server;
    private MutableLiveData<Club> clubMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();


    public ClubInfoViewModel() {
        init();
    }

    public MutableLiveData<Club> getClubMutableLiveData() {
        return clubMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    private void init() {
        server = "http://10.0.2.2:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clubAPI = retrofit.create(ClubAPI.class);
        userAPI = retrofit.create(UserAPI.class);
        commentAPI = retrofit.create(CommentAPI.class);
        clubImagesAPI = retrofit.create(ClubImagesAPI.class);
    }


    public void getClub(String name) {
        Call<JsonObject> getClubCall = clubAPI.getClub(name);
        getClubCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Club club = new Gson().fromJson(response.body(), Club.class);
                clubMutableLiveData.postValue(club);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getUser(String token) {
        Call<JsonObject> call = userAPI.getUser(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                User user = new Gson().fromJson(response.body(), User.class);
                userMutableLiveData.postValue(user);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void addClubToFavourites(String token, String clubName){
        Call<ResponseBody> call = userAPI.addClubToFavourites(token, clubName);
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

    public void deleteClubFromFavourites(String token, String clubName){
        Call<ResponseBody> call = userAPI.deleteClubFromFavourites(token, clubName);
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

    public void addComment(String text, String token, String clubName){
        Call<ResponseBody> call = commentAPI.addComment(text, token, clubName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getClub(clubName);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void addClubImage(String clubName, String imageUrl){
        Call<ResponseBody> call = clubImagesAPI.addClubImage(clubName, imageUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getClub(clubName);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void addEvent(String clubName, Event event){
        Call<ResponseBody> call = clubAPI.addEvent(clubName, event);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                getClub(clubName);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
