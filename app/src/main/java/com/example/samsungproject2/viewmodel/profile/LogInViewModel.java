package com.example.samsungproject2.viewmodel.profile;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.samsungproject2.network.UserAPI;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LogInViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<>();
    private MutableLiveData<String> errorText = new MutableLiveData<>();
    private UserAPI userAPI;
    private String server;

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public MutableLiveData<String> getErrorText() {
        return errorText;
    }

    public LogInViewModel() {
        init();
    }
    private void init(){
        server = "http://10.0.2.2:8080";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        userAPI = retrofit.create(UserAPI.class);
    }

    public void signIn(String email, String password){
        Log.d("help", email + " " + password);
        Call<ResponseBody> call = userAPI.signIn(email, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 400){
                        errorText.postValue(response.errorBody().string());
                    }
                    else
                        liveData.postValue(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
