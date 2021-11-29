package com.example.weatherapp.remote;


import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private OkHttpClient provideOkhttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(provideLoggingInterceptor())
                .build();
    }

    private Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(provideOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public WeatherApi provideApi() {
        return retrofit.create(WeatherApi.class);
    }
}

