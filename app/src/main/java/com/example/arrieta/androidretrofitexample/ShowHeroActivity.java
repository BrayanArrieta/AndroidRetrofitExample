package com.example.arrieta.androidretrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.arrieta.androidretrofitexample.Interfaces.Heroes;
import com.example.arrieta.androidretrofitexample.Models.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowHeroActivity extends AppCompatActivity {
    Gson gson;
    Retrofit retrofit;
    Heroes heroesService;
    TextView heroNameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_hero);
        gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.120:3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        heroesService = retrofit.create(Heroes.class);
        heroNameTextView=(TextView) findViewById(R.id.heroNameTextView);
        getHero(getIntent().getExtras().getLong("id"));
    }

    public void getHero(Long id){
        Call<List<Hero>> call=heroesService.get(id);
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                if (response.isSuccessful()){
                    Hero hero=response.body().get(0);
                    heroNameTextView.setText(hero.getName());
                }else{
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                //Failed
            }
        });

    }
}
