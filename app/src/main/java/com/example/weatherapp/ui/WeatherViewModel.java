package com.example.weatherapp.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.models.CurrentWeather;
import com.example.weatherapp.repositories.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    private final MainRepository repository;

    @Inject
    public WeatherViewModel(MainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<Resource<CurrentWeather>> getWeather() {
        return repository.getCurrentWeather();
    }

    public MutableLiveData<Resource<CurrentWeather>> getWeatherByName(String cityName) {
        return repository.getCurrentWeatherByCityName(cityName);
    }

}
