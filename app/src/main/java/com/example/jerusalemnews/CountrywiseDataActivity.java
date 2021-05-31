package com.example.jerusalemnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class CountrywiseDataActivity extends AppCompatActivity {

    public static final String COUNTRY_NAME = "country";
    public static final String COUNTRY_CONFIRMED = "cases";
    public static final String COUNTRY_ACTIVE = "active";
    public static final String COUNTRY_DECEASED = "deaths";
    public static final String COUNTRY_NEW_CONFIRMED = "todayCases";
    public static final String COUNTRY_TESTS = "tests";
    public static final String COUNTRY_NEW_DECEASED = "todayDeaths";
    public static final String COUNTRY_FLAGURL = "flag";
    public static final String COUNTRY_RECOVERED = "recovered";

    private RecyclerView recyclerView;
    private CountryL countrywiseAdapter;
    private RequestQueue requestQueue;
    private ArrayList<CountrywiseModel> countrywiseModelArrayList;
    ProgressDialog progressDialog;
    public static int confirmation = 0;
    public static String testValue;
    public static boolean isRefreshed;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrywise_data);




        recyclerView = findViewById(R.id.countrywise_recyclerview);
        swipeRefreshLayout = findViewById(R.id.countrywise_refresh);
        search = findViewById(R.id.search_editText);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countrywiseModelArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        showProgressDialog();
        extractData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                extractData();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CountrywiseDataActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        ArrayList<CountrywiseModel> filteredList = new ArrayList<>();
        for (CountrywiseModel item : countrywiseModelArrayList) {
            if (item.getCountry().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        countrywiseAdapter.filterList(filteredList);
    }

    private void extractData() {
        String dataURL = "https://corona.lmao.ninja/v2/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, dataURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    countrywiseModelArrayList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String countryName = jsonObject.getString("country");
                        String countryConfirmed = jsonObject.getString("cases");
                        String countryActive = jsonObject.getString("active");
                        String countryRecovered = jsonObject.getString("recovered");
                        String countryDeceased = jsonObject.getString("deaths");
                        String countryNewConfirmed = jsonObject.getString("todayCases");
                        String countryNewDeceased = jsonObject.getString("todayDeaths");
                        String countryTests = jsonObject.getString("tests");
                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");
                        testValue = countryTests;

                        int confirmedInt = Integer.parseInt(countryConfirmed);
                        countryConfirmed = NumberFormat.getInstance().format(confirmedInt);

                        int newConfirmedInt = Integer.parseInt(countryNewConfirmed);
                        countryNewConfirmed = NumberFormat.getInstance().format(newConfirmedInt);

                        int newDeceasedInt = Integer.parseInt(countryNewDeceased);
                        countryNewDeceased = NumberFormat.getInstance().format(newDeceasedInt);

                        int testsInt = Integer.parseInt(countryTests);
                        countryTests = NumberFormat.getInstance().format(testsInt);


                        countrywiseModelArrayList.add(new CountrywiseModel(countryName, countryConfirmed, countryActive, countryDeceased, countryNewConfirmed, countryNewDeceased, countryRecovered, countryTests, flagUrl));
                    }
                    if (!testValue.isEmpty()) {
                        Runnable progressRunnable = new Runnable() {

                            @Override
                            public void run() {
                                progressDialog.cancel();
                                countrywiseAdapter = new CountryL(CountrywiseDataActivity.this, countrywiseModelArrayList);
                                recyclerView.setAdapter(countrywiseAdapter);
                                //countrywiseAdapter.setOnItemClickListner(CountrywiseDataActivity, this);
                            }
                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 500);
                        confirmation = 1;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void onItemClick(int position) {
        Intent perCountryIntent = new Intent(this, PerCountryData.class);
        CountrywiseModel clickedItem = countrywiseModelArrayList.get(position);

        perCountryIntent.putExtra(COUNTRY_NAME, clickedItem.getCountry());
        perCountryIntent.putExtra(COUNTRY_CONFIRMED, clickedItem.getConfirmed());
        perCountryIntent.putExtra(COUNTRY_ACTIVE, clickedItem.getActive());
        perCountryIntent.putExtra(COUNTRY_RECOVERED, clickedItem.getRecovered());
        perCountryIntent.putExtra(COUNTRY_DECEASED, clickedItem.getDeceased());
        perCountryIntent.putExtra(COUNTRY_NEW_CONFIRMED, clickedItem.getNewConfirmed());
        perCountryIntent.putExtra(COUNTRY_NEW_DECEASED, clickedItem.getNewDeceased());
        perCountryIntent.putExtra(COUNTRY_TESTS, clickedItem.getTests());
        perCountryIntent.putExtra(COUNTRY_FLAGURL, clickedItem.getFlag());


        startActivity(perCountryIntent);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(CountrywiseDataActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(CountrywiseDataActivity.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }
}
