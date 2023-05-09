package com.example.samsungproject2.viewmodel.clubs;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.deserializers.ByteArrayToBase64TypeAdapter;
import com.example.samsungproject2.model.Club;
import com.example.samsungproject2.network.ClubAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubsViewModel extends ViewModel {
    private String server;
    private ClubAPI clubAPI;
    private MutableLiveData<Club> clubMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Club>> clubListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();

    public MutableLiveData<Club> getClubMutableLiveData() {
        return clubMutableLiveData;
    }

    public MutableLiveData<List<Club>> getClubListMutableLiveData() {
        return clubListMutableLiveData;
    }

    public ClubsViewModel(){
        init();
        getAllClubs();
        Log.d("help", "ClubsViewModel: ");
    }

    private void init() {
        server = "http://10.0.2.2:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clubAPI = retrofit.create(ClubAPI.class);
    }

    private void getClub(String name) {
        Call<JsonObject> getClubCall = clubAPI.getClub(name);
        getClubCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Club club = new Gson().fromJson(response.body().getAsJsonObject(), Club.class);
                clubMutableLiveData.postValue(club);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void getAllClubs() {
        Call<JsonArray> getAllClubsCall = clubAPI.getAllClubs();
        getAllClubsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                List<Club> clubs = new ArrayList<>();
                Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
                        new ByteArrayToBase64TypeAdapter()).create();
                for (JsonElement object : response.body()) {
                    Club club = gson.fromJson(object, Club.class);
                    clubs.add(club);
                }
                clubListMutableLiveData.postValue(clubs);
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
