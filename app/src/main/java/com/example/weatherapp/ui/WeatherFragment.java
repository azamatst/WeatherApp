package com.example.weatherapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.databinding.FragmentWeatherBinding;
import com.example.weatherapp.models.CurrentWeather;

import java.text.DecimalFormat;


public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;

    WeatherViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        Log.e("TAG", "onCreate: ");

    }

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getWeather().observe(getViewLifecycleOwner(), response -> {

            switch (response.status) {

                case SUCCESS:
                    String temp = new DecimalFormat("##").format(response.data.getMain().getTemp() - 273.15);
                    binding.tvDegree.setText(temp);
                    Log.e("TAG", "success: " + response.data.getMain().getTemp().toString());
                    Toast.makeText(requireContext(), response.data.getMain().getTemp().toString(), Toast.LENGTH_SHORT).show();
                    break;

                case ERROR:
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    break;

                case LOADING:
                    Toast.makeText(requireContext(), "load", Toast.LENGTH_SHORT).show();
                    break;


            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}