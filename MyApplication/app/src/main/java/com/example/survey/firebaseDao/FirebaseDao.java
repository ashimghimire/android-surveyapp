package com.example.survey.firebaseDao;

import com.example.survey.utils.FirebaseDb;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseDao {
    public Task<QuerySnapshot> getAllSurveys(){
        return FirebaseFirestore.getInstance().collection(FirebaseDb.getInstance().getSurveyCollection()).get();

    }

    public DocumentReference getSurveyById(String id){
        return FirebaseFirestore.getInstance().collection(FirebaseDb.getInstance().getSurveyCollection()).document(id);
    }

}
