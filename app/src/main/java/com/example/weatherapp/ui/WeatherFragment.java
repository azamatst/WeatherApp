package com.example.weatherapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.databinding.FragmentWeatherBinding;
import com.example.weatherapp.models.CurrentWeather;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherFragmentArgs args;

    WeatherViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        Log.e("TAG", "onCreate: ");
        args = WeatherFragmentArgs.fromBundle(getArguments());

    }

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getWeather().observe(getViewLifecycleOwner(), response -> {

            switch (response.status) {

                case SUCCESS:
                    setData(response.data);
                    break;

                case ERROR:
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                    break;

                case LOADING:
                    Toast.makeText(requireContext(), "load", Toast.LENGTH_SHORT).show();
                    break;


            }
        });


        Log.e("args", args.toString());

        binding.locations.setOnClickListener(v -> {
            openFragment();
        });

        if (args.getCityId() != null) {
            binding.tvLocation.setText(args.getCityId());
            viewModel.getWeatherByName(args.getCityId()).observe(getViewLifecycleOwner(), response -> {
                switch (response.status) {

                    case SUCCESS:
                        setData(response.data);
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

    }

    @SuppressLint("SetTextI18n")
    private void setData(CurrentWeather data){
        binding.tvDate.setText(getDate(System.currentTimeMillis()));
        binding.tvLocation.setText(data.getName());
        String weatherImgUri = "http://openweathermap.org/img/wn/" + data.getWeather().get(0).getIcon() + "@2x.png";
        Glide.with(requireContext()).load(weatherImgUri).into(binding.ivStatus);
        Log.e("icon", "setData: " + weatherImgUri);
        Toast.makeText(requireContext(), weatherImgUri, Toast.LENGTH_SHORT).show();
        binding.tvStatus.setText(data.getWeather().get(0).getMain());
        binding.ivMain.setImageResource(getImageId(data.getDt(), data.getSys().getSunrise(), data.getSys().getSunset()));
        binding.tvDegree.setText(changeToCelsius(data.getTemp().getTemp()));
        binding.tvDegUp.setText(changeToCelsius(data.getTemp().getTempMax()) + getString(R.string.deg_up));
        binding.tvDegDown.setText(changeToCelsius(data.getTemp().getTempMin()) + getString(R.string.deg_down));
        binding.tvHumidity.setText(data.getTemp().getHumidity() + getString(R.string.percent));
        binding.tvPressure.setText(data.getTemp().getPressure() + getString(R.string.mbar));
        binding.tvWind.setText(data.getWind().getSpeed() + getString(R.string.m_s));
        binding.tvSunrise.setText(getTime(requireContext(), data.getSys().getSunrise()));
        binding.tvSunset.setText(getTime(requireContext(), data.getSys().getSunset()));
    }

    private String changeToCelsius(Double kelvin) {
        return new DecimalFormat("##").format(kelvin - 273.15);
    }

    public static String getTime(Context context, Long date) {
        return DateUtils.formatDateTime(context, date * 1000, DateUtils.FORMAT_SHOW_TIME);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDate(Long dt) {
        return new SimpleDateFormat("EEEE,MMM d | h:mm a").format(dt);
    }

    private int getImageId(long date, long sunrise, long sunset) {
        if (date < sunrise || date > sunset) return R.drawable.ic_night;
        else return R.drawable.ic_day;
    }


    private void openFragment() {
        NavHostFragment navHostFragment =
                (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        navController.navigate(R.id.city_fragment);

    }

}