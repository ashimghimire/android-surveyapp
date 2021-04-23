package com.example.survey.ui.home;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.survey.R;

public class SurveyDetail extends Fragment {

    private SurveyDetailViewModel mViewModel;
    TextView sTitle;
    TextView lat;
    TextView lon;
    TextView address;
    RatingBar bar;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_survey_detail, container, false);
         sTitle=(TextView)root.findViewById(R.id.txtComments);
         lat=(TextView)root.findViewById(R.id.lblLatitude2);
         lon=(TextView)root.findViewById(R.id.txtlongitude);
         bar=(RatingBar)root.findViewById(R.id.secRating);
         address=(TextView)root.findViewById(R.id.txtAddress);
         mViewModel=ViewModelProviders.of((FragmentActivity)container.getContext()).get(SurveyDetailViewModel.class);
         mViewModel.getSurveyModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<SurveyModel>() {
            @Override
            public void onChanged(SurveyModel surveyModel) {
                sTitle.setText(surveyModel.getComments());
                if(surveyModel.getLocation()!=null) {
                    lat.setText(surveyModel.getLocation().getLatitude() + "");
                    lon.setText(surveyModel.getLocation().getLongitude() + "");
                }
                address.setText(surveyModel.getAddress());
                bar.setRating(surveyModel.getSecurityRating());

            }
        });
         return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
