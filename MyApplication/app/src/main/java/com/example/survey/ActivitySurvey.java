package com.example.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.survey.ui.home.MyHomeAdapter;
import com.example.survey.utils.FirebaseDb;
import com.example.survey.utils.MyRequestQueue;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ActivitySurvey extends AppCompatActivity {
    private TextView address;
    private EditText myeditText;
    private Button btnSubmit;
    private RatingBar ratingBar;
    private Button btnTag;
    private ProgressBar progressBar;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private RequestQueue requestQueue;
    private JsonObjectRequest getLocationRequest;
    private StringRequest getDiagnosisRequest;
    private RadioButton rgMale;
    private RadioButton rgFemale;
    private EditText txtSymptomId;
    private EditText txtAge;
    private RadioGroup rgGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        address = (TextView) findViewById(R.id.txtAddress);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnTag=(Button)findViewById(R.id.btnTag);
        txtSymptomId=(EditText)findViewById(R.id.editTextSymptomsId);
        txtAge=(EditText)findViewById(R.id.editAge);
        rgMale=(RadioButton)findViewById(R.id.rgMale);
        rgGroup=(RadioGroup)findViewById(R.id.rgGender);
        rgFemale=(RadioButton)findViewById(R.id.rgFemale);
        myeditText = (EditText) findViewById(R.id.txtTitle);
        Random rand = new Random();
         getLocationRequest=new JsonObjectRequest
                (Request.Method.GET, getString(R.string.MOCK_LOCATION_URL)+(rand.nextInt(20))+"", null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       try{
                        address.setText(response.getString("Address"));
                        requestQueue.cancelAll("LOCATION");

                       }catch (JSONException e){
                            e.printStackTrace();
                       }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });


        // Formulate the request and handle the response.
         getDiagnosisRequest = new StringRequest(Request.Method.GET, getString(R.string.mockDiagnosis),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        FirebaseFirestore db = FirebaseDb.getInstance().getFirestoreDb(getApplicationContext());
                        float rating=calculateRating(response);
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("Address", address.getText().toString());
                        map.put("securityRating", rating);
                        map.put("comments", myeditText.getText().toString());
                        db.collection(FirebaseDb.getInstance().getSurveyCollection()).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        requestQueue.cancelAll("DIAGNOSIS");
                        requestQueue.getCache().clear();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                requestQueue = MyRequestQueue.getInstance(getApplicationContext()).getNetworkRequestQueue();
                requestQueue.start();
                getDiagnosisRequest.setTag("DIAGNOSIS");
                requestQueue.add(getDiagnosisRequest);

            }
        });

        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = MyRequestQueue.getInstance(getApplicationContext()).getNetworkRequestQueue();
               requestQueue.start();
                getLocationRequest.setTag("LOCATION");
                requestQueue.getCache().clear();
               requestQueue.add(getLocationRequest);

            }
        });


}

    public float calculateRating(String response){
        //Here we calculate the security rating for the application.
        return  1;

    }








}







