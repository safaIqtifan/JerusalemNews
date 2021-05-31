package com.example.jerusalemnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }

    }
}
