package com.example.samsungproject2.viewmodel.map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.network.ClubAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<Club>> mutableLiveData = new MutableLiveData<>();
    private String server;
    private ClubAPI clubAPI;

    public MapViewModel() {
        init();
        getAllClubs();
    }

    public MutableLiveData<List<Club>> getMutableLiveData() {
        return mutableLiveData;
    }

    private void init() {
        server = "http://158.160.29.103:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clubAPI = retrofit.create(ClubAPI.class);
    }

    private void getAllClubs() {
        Call<JsonArray> getAllClubsCall = clubAPI.getAllClubs();
        getAllClubsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                List<Club> clubs = new ArrayList<>();
                for (JsonElement element : response.body()) {
                    Club club = new Gson().fromJson(element, Club.class);
                    clubs.add(club);
                }
                mutableLiveData.postValue(clubs);
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }


}
