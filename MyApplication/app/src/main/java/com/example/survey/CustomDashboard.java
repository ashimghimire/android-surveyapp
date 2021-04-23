package com.example.survey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.survey.R;
import com.example.survey.utils.SharedPrefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView drawerHeaderImageView;
    private TextView displayName;
    private TextView displayEmail;
    private SharedPrefs sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dashboard);
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPref=new SharedPrefs(getApplicationContext());
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(CustomDashboard.this, ActivitySurvey.class);
               startActivity(i);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.privacyPolicy)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View navigationHeaderView=navigationView.getHeaderView(0);

        // add custom Image and text
        displayEmail=(TextView)navigationHeaderView.findViewById(R.id.txtDisplayEmail);
        displayName=(TextView)navigationHeaderView.findViewById(R.id.txtDisplayName);

        drawerHeaderImageView = (ImageView)navigationHeaderView.findViewById(R.id.imageViewAvatar);
        if(currentUser.getDisplayName()!=null&&currentUser.getEmail()!=null) {
            displayName.setText(currentUser.getDisplayName().toString());
            displayEmail.setText(currentUser.getEmail().toString());
            if (currentUser.getPhotoUrl() != null) {
               // drawerHeaderImageView.setImageBitmap(getBitmapFromURL(currentUser.getPhotoUrl().getPath()));
                Toast.makeText(CustomDashboard.this, currentUser.getPhotoUrl().getPath(), Toast.LENGTH_LONG).show();

            }
        }

    }

//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_dashboard, menu);
        getMenuInflater().inflate(R.menu.activity_custom_dashboard_drawer,menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        sharedPref.deleteUser();
        Intent i=new Intent(CustomDashboard.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                logout();
                return true;
            case R.id.privacyPolicy:
                NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                navController.navigate(R.id.privacyPolicy);
                Log.d("fdskfdnlfk", "reschvdfd");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
