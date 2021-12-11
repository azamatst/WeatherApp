package com.example.weatherapp.remote;


import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
                .addInterceptor(queryInterceptor())
                .build();
    }

    private Interceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    private Interceptor queryInterceptor() {
        Interceptor chain = chain1 -> {
            Request request = chain1.request();
            HttpUrl url = request
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", "9defb1e4620606b934e57c972b96241b")
                    .build();


            request = request.newBuilder().url(url).build();
            return chain1.proceed(request);
        };
        return chain;
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

