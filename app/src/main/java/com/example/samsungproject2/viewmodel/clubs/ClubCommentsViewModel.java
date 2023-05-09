package com.example.samsungproject2.viewmodel.clubs;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.network.ClubAPI;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubCommentsViewModel extends ViewModel {
    private ClubAPI clubAPI;
    private String server;
    private MutableLiveData<Club> clubMutableLiveData = new MutableLiveData<>();

    public ClubCommentsViewModel() {
        init();
    }

    public MutableLiveData<Club> getClubMutableLiveData() {
        return clubMutableLiveData;
    }

    private void init() {
        server = "http://10.0.2.2:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clubAPI = retrofit.create(ClubAPI.class);
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
}
