package com.example.survey.utils;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDb {

    private static String PROJECT_ID="firebase-authentication-300411";
    private static final FirebaseDb INSTANCE = new FirebaseDb();
    private static String SURVEY_COLLECTION="geotag";
    public static FirebaseDb getInstance() {
        return INSTANCE;
    }
    private FirebaseDb() {}
    public String getSurveyCollection(){
        return SURVEY_COLLECTION;
    }
    public FirebaseFirestore getFirestoreDb(Context context){
        FirebaseFirestore db;
        db= FirebaseFirestore.getInstance();
        return db;
    }

}
