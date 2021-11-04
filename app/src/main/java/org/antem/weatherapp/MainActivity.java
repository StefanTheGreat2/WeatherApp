package org.antem.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    TextView name, temp, sky, description;
    EditText citySearch;
    Button search;

    private static final String URL = "https://api.openweathermap.org/";

    private static final String MY_KEY="GET_KEY";

    private WeatherService mWeatherService;
    Root mRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        sky = findViewById(R.id.skyTv);
        description = findViewById(R.id.description);
        citySearch = findViewById(R.id.citySearch);
        search = findViewById(R.id.search);
        name=findViewById(R.id.cityName);
        search.setOnClickListener(searchBtn);

        mWeatherService = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService.class);

        getWeather();
    }

    View.OnClickListener searchBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getWeather();

        }
    };

    private void getWeather() {
        String city;
        if (citySearch.getText().toString().trim().equals("")) {
            // Default City
            city = "new york";

        } else {
            city = citySearch.getText().toString().trim();
        }

        // Get key from openweathermap.org

        mWeatherService.getWeather(city, "metric", MY_KEY)
                .enqueue(new Callback<Root>() {
                    @Override
                    public void onResponse(Call<Root> call, Response<Root> response) {

                        mRoot = response.body();
                        if (mRoot != null) {
                            temp.setText(String.valueOf((int) mRoot.getMain().getTemp() + "°C"));
                            description.setText(mRoot.getMain().toString());
                            sky.setText(mRoot.getWeather().get(0).getDescription());
                            name.setText(mRoot.getName());


                        }

                    }

                    @Override
                    public void onFailure(Call<Root> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + t.toString());

                    }
                });
    }
}
