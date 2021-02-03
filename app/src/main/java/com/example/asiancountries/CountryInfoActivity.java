package com.example.asiancountries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CountryInfoActivity extends AppCompatActivity {

    private String url1 = "https://restcountries.eu/rest/v2/name/";
    private String url2 = "?fullText=true";

    private TextView toolbarTextView, countryNameTextView, capitalTextView, regionTextView, subregionTextView, languageTextView, populationTextView, borderTextView;
    private ImageView toolbarImageView, flagImageView;

    String name;
    String flag;
    String capitalName;
    String region;
    String subregion;
    String population;

    List<String> languages = new ArrayList<>();
    List<String> borders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_info);

        String countryName = getIntent().getStringExtra("name");

        initWidgets();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1 + countryName + url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if (response != null) {

                                Log.d("Success", "" + response);

                                JSONArray jsonArray = new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    name = object.getString("name");
                                    flag = object.getString("flag");
                                    flag = flag.trim();
                                    capitalName = object.getString("capital");
                                    region = object.getString("region");
                                    subregion = object.getString("subregion");
                                    population = object.getString("population");

                                    Log.d("CountryInfo", ""+name+capitalName);

                                    JSONArray languageArray = object.getJSONArray("languages");
                                    Log.d("length", "" + languageArray.length());
                                    for(int j=0;j<languageArray.length();j++){

                                        JSONObject languageObject = languageArray.getJSONObject(j);
                                        languages.add(j,languageObject.getString("name"));

                                        Log.d("CountryInfo", ""+languageObject.getString("name"));

                                    }

                                    JSONArray borderArray = object.getJSONArray("borders");
                                    Log.d("length", "" + borderArray.length());
                                    for(int j=0;j<borderArray.length();j++){

                                        borders.add(j,borderArray.getString(j));

                                        Log.d("CountryInfo", ""+borderArray.getString(j));

                                    }


                                }

                                if(name!=null) {

                                    toolbarTextView.setText(name);
                                    countryNameTextView.setText(name);

                                }
                                if(flag!=null) {

                                    GlideToVectorYou
                                            .init()
                                            .with(CountryInfoActivity.this)
                                            .withListener(new GlideToVectorYouListener() {
                                                @Override
                                                public void onLoadFailed() {
                                                    //Toast.makeText(CountryInfoActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onResourceReady() {
                                                    //Toast.makeText(CountryInfoActivity.this, "Image ready", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .setPlaceHolder(R.drawable.ic_baseline_photo_24, R.drawable.ic_baseline_photo_24)
                                            .load(Uri.parse(flag), toolbarImageView);

                                    GlideToVectorYou
                                            .init()
                                            .with(CountryInfoActivity.this)
                                            .withListener(new GlideToVectorYouListener() {
                                                @Override
                                                public void onLoadFailed() {
                                                    //Toast.makeText(CountryInfoActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onResourceReady() {
                                                    //Toast.makeText(CountryInfoActivity.this, "Image ready", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .setPlaceHolder(R.drawable.ic_baseline_photo_24, R.drawable.ic_baseline_photo_24)
                                            .load(Uri.parse(flag), flagImageView);

                                }

                                if(capitalName!=null) {
                                    capitalTextView.setText(capitalName);
                                }

                                if(region!=null) {
                                    regionTextView.setText(region);
                                }

                                if(subregion!=null){
                                    subregionTextView.setText(subregion);
                                }

                                if (population != null){
                                    populationTextView.setText(population);
                                }

                                if(languages!=null){

                                    StringBuilder str = new StringBuilder();
                                    for(int k = 0;k<languages.size();k++){
                                        str.append(languages.get(k)).append(" ");
                                    }

                                    languageTextView.setText(str.toString());

                                }

                                if(borders!=null){

                                    StringBuilder str = new StringBuilder();
                                    for(int k = 0;k<borders.size();k++){
                                        str.append(borders.get(k)).append(" ");
                                    }

                                    borderTextView.setText(str.toString());

                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Error", ""+ error.toString());

            }
        });

        requestQueue.add(stringRequest);

    }

    private void initWidgets(){

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbarTextView = findViewById(R.id.toolbarTextView);
        toolbarImageView = findViewById(R.id.toolbarImageView);

        countryNameTextView = findViewById(R.id.countryTextView);
        capitalTextView = findViewById(R.id.capitalTextView);
        populationTextView = findViewById(R.id.populationTextView);
        regionTextView = findViewById(R.id.regionTextView);
        subregionTextView = findViewById(R.id.subregionTextView);
        flagImageView = findViewById(R.id.flagImageView);
        languageTextView = findViewById(R.id.languageTextView);
        borderTextView = findViewById(R.id.bordersTextView);

    }


}