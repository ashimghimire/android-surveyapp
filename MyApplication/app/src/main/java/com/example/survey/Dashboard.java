package com.example.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_xml);
        FirebaseUser fbuser  = (FirebaseUser) getIntent().getParcelableExtra("auth");
        Toast.makeText(this,fbuser.getDisplayName(),Toast.LENGTH_LONG).show();
    }
}
