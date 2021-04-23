package com.example.survey.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SurveyDetailViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<SurveyModel> surveyModelMutableLiveData = new MutableLiveData<SurveyModel>();

    public MutableLiveData<SurveyModel> getSurveyModelMutableLiveData() {
        return surveyModelMutableLiveData;
    }

    public void setSurveyModelMutableLiveData(SurveyModel surveyModel) {
        surveyModelMutableLiveData.setValue(surveyModel);
    }

}
