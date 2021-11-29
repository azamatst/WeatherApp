package com.example.weatherapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.App;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    public MainRepository() {

    }

    public MutableLiveData<Resource<CurrentWeather>> getCurrentWeather() {
        MutableLiveData<Resource<CurrentWeather>> liveData = new MutableLiveData<>();
        liveData.setValue(new Resource<>(Resource.Status.LOADING, null, null));
        App.api.getCurrentWeather("Bishkek, Kyrgyzstan", "9defb1e4620606b934e57c972b96241b").enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(new Resource<>(Resource.Status.SUCCESS, response.body(), null));
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                liveData.setValue(new Resource<>(Resource.Status.ERROR, null, t.getLocalizedMessage()));
            }
        });
        return liveData;

    }
}
