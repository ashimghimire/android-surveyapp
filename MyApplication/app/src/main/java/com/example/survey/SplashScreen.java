package com.example.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.survey.R;
import com.example.survey.utils.SharedPrefs;

public class SplashScreen extends AppCompatActivity {

    private SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedPrefs = new SharedPrefs(getApplicationContext());
        int secondsDelayed=1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(sharedPrefs.isLoggedIn()){
                    startActivity(new Intent(getApplicationContext(), CustomDashboard.class));
                    finish();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }, secondsDelayed * 1000);

    }
}
