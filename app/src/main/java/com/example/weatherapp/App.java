package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.remote.RetrofitClient;
import com.example.weatherapp.remote.WeatherApi;
import com.example.weatherapp.repositories.MainRepository;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {

}
