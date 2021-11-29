package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.remote.RetrofitClient;
import com.example.weatherapp.remote.WeatherApi;
import com.example.weatherapp.repositories.MainRepository;

public class App extends Application {

    public static WeatherApi api;
    public static MainRepository repository;


    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideApi();
        repository = new MainRepository();
    }
}
