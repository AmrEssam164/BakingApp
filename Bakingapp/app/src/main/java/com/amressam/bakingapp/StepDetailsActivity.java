package com.amressam.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.fragments.StepDetailsFragment;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {
    ArrayList<Steps> mStepsArrayList;
    int stepIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        getSupportActionBar().setTitle("Steps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mStepsArrayList = (ArrayList<Steps>) intent.getSerializableExtra(MainActivity.STEPS_INTENT);
        stepIndex = intent.getIntExtra(IngredientsStepsActivity.STEP_POSITION,0);

        if(savedInstanceState==null){
            setFragment();
        }

    }

    private void setFragment(){
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setStepsArrayList(mStepsArrayList);
        stepDetailsFragment.setStepIndex(stepIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return false;
    }
}
