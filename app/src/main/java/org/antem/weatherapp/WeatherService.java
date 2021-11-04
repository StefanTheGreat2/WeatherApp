package org.antem.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/weather")
    Call<Root> getWeather(
            @Query("q") String city,
            @Query("units")String units,
            @Query("appid") String key
    );
}
