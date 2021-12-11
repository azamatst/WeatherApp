package com.example.weatherapp.remote;

import com.example.weatherapp.models.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather")
    Call<CurrentWeather> getCurrentWeather(
            @Query("q") String cityName
    );


}
