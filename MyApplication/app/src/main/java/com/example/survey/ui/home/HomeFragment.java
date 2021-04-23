package com.example.survey.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.survey.R;
import com.example.survey.firebaseDao.FirebaseDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mRecycleView;
    private MyHomeAdapter myHomeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_home, container, false);
         TextView textView = root.findViewById(R.id.txtTotalSurvetText);
         final  TextView tvTotal=root.findViewById(R.id.txtTotalSurvey);
         textView.setText("Total Surveyed");
         mRecycleView=(RecyclerView) root.findViewById(R.id.lvSurveys);
         layoutManager=new LinearLayoutManager(getContext());
         mRecycleView.setLayoutManager(layoutManager);
         mRecycleView.setAdapter(myHomeAdapter);
         final  List<SurveyModel> surveyModel=new ArrayList<>();
         Task<QuerySnapshot> myTask=new FirebaseDao().getAllSurveys();
         myTask.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document:task.getResult()){
                        SurveyModel surveyModel1 = new SurveyModel();
                        surveyModel1 = document.toObject(SurveyModel.class);
                        surveyModel.add(surveyModel1);
                    }

                    for(SurveyModel surveyModel1:surveyModel){

                    }
                    tvTotal.setText(surveyModel.size()+"");
                    myHomeAdapter=new MyHomeAdapter(surveyModel, container.getContext());
                    mRecycleView.setAdapter(myHomeAdapter);
                }

            }
        });
        return root;
    }


    }



