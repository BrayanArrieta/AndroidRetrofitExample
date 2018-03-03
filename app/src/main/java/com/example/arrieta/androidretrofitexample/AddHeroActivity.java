package com.example.arrieta.androidretrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arrieta.androidretrofitexample.Interfaces.Heroes;
import com.example.arrieta.androidretrofitexample.Models.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddHeroActivity extends AppCompatActivity {
    Gson gson;
    Retrofit retrofit;
    Heroes heroesService;
    Button saveButton;
    EditText nameHeroEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hero);
        gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.120:3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        heroesService = retrofit.create(Heroes.class);
        saveButton=(Button)findViewById(R.id.saveButton);
        nameHeroEditText=(EditText)findViewById(R.id.heroNameEditText);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHero(new Hero(nameHeroEditText.getText().toString()));
            }
        });
    }

    public void addHero (Hero hero){
        Call<Hero> call=heroesService.post(hero);
        call.enqueue(new Callback<Hero>() {
            @Override
            public void onResponse(Call<Hero> call, Response<Hero> response) {
                if(response.isSuccessful()){
                    //Yes
                }else{
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure(Call<Hero> call, Throwable t) {
                //Failed
            }
        });

    }
}
