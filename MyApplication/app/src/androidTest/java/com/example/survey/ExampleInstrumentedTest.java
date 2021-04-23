package com.example.survey;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.survey.ActivitySurvey;
import com.example.survey.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
class ExampleInstrumentedTest {

    @Rule
    public ActivityScenarioRule<ActivitySurvey> activitySurveyActivityScenarioRule=new ActivityScenarioRule<ActivitySurvey>(ActivitySurvey.class);

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void listGoesOverTheFold() {

    }

}
