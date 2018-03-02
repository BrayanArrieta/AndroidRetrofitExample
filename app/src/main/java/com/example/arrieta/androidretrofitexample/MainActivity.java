package com.example.arrieta.androidretrofitexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    Gson gson;
    Retrofit retrofit;
    Heroes heroesService;
    ListView listViewHeroes;
    HeroesAdapter heroesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gson= new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit= new Retrofit.Builder()
                .baseUrl("http://192.168.1.120:3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        heroesService= retrofit.create(Heroes.class);
        listViewHeroes=(ListView)findViewById(R.id.listViewHeroes);
        getHeroes ();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void getHeroes (){
        Call<List<Hero>> call=heroesService.get(null);
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                if(response.isSuccessful()){
                    List<Hero> heroes=response.body();
                    listViewHeroes.setAdapter(new HeroesAdapter(heroes));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class HeroesAdapter extends ArrayAdapter<Hero> {
        private List<Hero> heroes;
        HeroesAdapter(List<Hero> heroes) {
            super(MainActivity.this, R.layout.row_hero);
            this.heroes=heroes;
        }
        public View getView(int position, View convertView, ViewGroup parent){
            View view=convertView;
            if(view==null){
                LayoutInflater inflater=getLayoutInflater();
                view=inflater.inflate(R.layout.row_hero, null);
            }
            Hero hero = this.heroes.get(position);
            TextView textView = (TextView)view.findViewById(R.id.textView);
            textView.setText(hero.getName());
            return view;
        }
    }

}
