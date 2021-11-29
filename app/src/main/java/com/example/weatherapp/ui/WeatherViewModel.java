package com.example.weatherapp.ui;

import static com.example.weatherapp.App.repository;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.models.CurrentWeather;

public class WeatherViewModel extends ViewModel {


    public WeatherViewModel() {
    }

    public MutableLiveData<Resource<CurrentWeather>> getWeather() {
        return repository.getCurrentWeather();
    }

}
