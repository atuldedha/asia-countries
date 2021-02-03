package com.example.asiancountries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Adapter adapter;

    String url = "https://restcountries.eu/rest/v2/region/asia";

    List<Country> allCountries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if(!response.isEmpty()) {

                                if(allCountries.size() != 0){
                                    allCountries.clear();
                                }

                                Log.d("Success", "" + response);

                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject r = jsonArray.getJSONObject(i);

                                    String name = r.getString("name");
                                    String flag = r.getString("flag");

                                    Log.d("Name", "" + name);

                                    Country country = new Country(name, flag);
                                    allCountries.add(country);

                                }
                                if (allCountries != null) {
                                    Adapter adapter = new Adapter(allCountries, MainActivity.this);

                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            Log.d("Exception", "" + e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "" + error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}