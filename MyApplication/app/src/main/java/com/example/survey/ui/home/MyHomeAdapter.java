package com.example.survey.ui.home;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.survey.R;

import java.util.List;

    public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.myViewHolder> {
    private List<SurveyModel> surveyModel;
    private Context context;
    public MyHomeAdapter(List<SurveyModel> surveyModel, Context context){
        this.surveyModel=surveyModel;
        this.context=context;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        myViewHolder vh = new myViewHolder(v, surveyModel,context);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
       holder.mTitle.setText(surveyModel.get(position).getComments());
       holder.securityRating.setRating(surveyModel.get(position).getSecurityRating());
       holder.securityRating.setEnabled(false);
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private RatingBar securityRating;
        private ImageButton mSettings;
        private List<SurveyModel> surveyModel;
        private Context context;

        public myViewHolder(@NonNull View itemView, List<SurveyModel> surveyModel, Context context) {
            super(itemView);
            mTitle=itemView.findViewById(R.id.txtSurveyName);
            securityRating=itemView.findViewById(R.id.ratingBar);
            this.context=context;
            this.surveyModel=surveyModel;
            itemView.setOnClickListener(this);
        }

        public Context getContext(){
            return context;
        }

        @Override
        public void onClick(View v) {
            SurveyDetailViewModel surveyDetailViewModel = ViewModelProviders.of((FragmentActivity) context).get(SurveyDetailViewModel.class);
            surveyDetailViewModel.setSurveyModelMutableLiveData(surveyModel.get(getAdapterPosition()));
            Navigation.findNavController(v).navigate(R.id.action_navigate_to_survey_detail);
        }
    }
    @Override
    public int getItemCount() {
        return surveyModel.size();
    }
}
