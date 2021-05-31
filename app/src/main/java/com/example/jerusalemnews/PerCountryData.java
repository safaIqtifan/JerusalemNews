package com.example.jerusalemnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.Objects;


public class PerCountryData extends AppCompatActivity {
    TextView perCountryConfirmed, perCountryActive, perCountryDeceased, perCountryNewConfirmed, perCountryTests, perCountryNewDeceased, perCountryRecovered;
    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_country_data);

        Intent intent = getIntent();
        String countryName = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_NAME);
        String countryConfirmed = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_CONFIRMED);
        String countryActive = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_ACTIVE);
        String countryDeceased = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_DECEASED);
        String countryRecovery = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_RECOVERED);
        String countryNewConfirmed = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_NEW_CONFIRMED);
        String countryNewDeceased = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_NEW_DECEASED);
        String countryTests = intent.getStringExtra(CountrywiseDataActivity.COUNTRY_TESTS);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Objects.requireNonNull(getSupportActionBar()).setTitle(countryName);
        perCountryConfirmed = findViewById(R.id.percountry_confirmed_textview);
        perCountryActive = findViewById(R.id.percountry_active_textView);
        perCountryRecovered = findViewById(R.id.percountry_recovered_textView);
        perCountryDeceased = findViewById(R.id.percountry_death_textView);
        perCountryNewConfirmed = findViewById(R.id.percountry_confirmed_new_textView);
        perCountryTests = findViewById(R.id.percountry_tests_textView);
        perCountryNewDeceased = findViewById(R.id.percountry_death_new_textView);
        mPieChart = findViewById(R.id.piechart_percountry);

        String activeCopy = countryActive;
        String recoveredCopy = countryRecovery;
        String deceasedCopy = countryDeceased;

        int activeInt = Integer.parseInt(countryActive);
        countryActive = NumberFormat.getInstance().format(activeInt);

        int recoveredInt = Integer.parseInt(countryRecovery);
        countryRecovery = NumberFormat.getInstance().format(recoveredInt);

        int deceasedInt = Integer.parseInt(countryDeceased);
        countryDeceased = NumberFormat.getInstance().format(deceasedInt);

        mPieChart.addPieSlice(new PieModel("Active", Integer.parseInt(activeCopy), Color.parseColor("#007afe")));
        mPieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recoveredCopy), Color.parseColor("#08a045")));
        mPieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(deceasedCopy), Color.parseColor("#F6404F")));

        mPieChart.startAnimation();

        perCountryConfirmed.append(countryConfirmed);
        perCountryActive.append(countryActive);
        perCountryDeceased.append(countryDeceased);
        perCountryTests.append(countryTests);
        perCountryNewConfirmed.append("+" + countryNewConfirmed);
        perCountryNewDeceased.append("+" + countryNewDeceased);
        perCountryRecovered.append(countryRecovery);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
