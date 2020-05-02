package com.amressam.bakingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.fragments.StepDetailsFragment;
import com.amressam.bakingapp.fragments.StepsFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class IngredientsStepsActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {

    ArrayList<Ingredients> recipeIngredients;
    ArrayList<Steps> recipeSteps;
    TextView ingredients;
    public static boolean mTwoPane;
    public static final String STEP_POSITION = "step_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_steps);

        ingredients = (TextView) findViewById(R.id.ingredients);

        Intent intent = getIntent();
        recipeIngredients = (ArrayList<Ingredients>) intent.getSerializableExtra(MainActivity.INGREDIENTS_INTENT);
        recipeSteps = (ArrayList<Steps>) intent.getSerializableExtra(MainActivity.STEPS_INTENT);

        getSupportActionBar().setTitle(recipeIngredients.get(0).getRecipe_name());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setIngredients();

        if (findViewById(R.id.step_details_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                setStepsFragment();
                setStepDetailsFragment();
            }
        } else {
            mTwoPane = false;
            if (savedInstanceState == null) {
                setStepsFragment();
            }
        }
    }

    private void setIngredients() {
        for (int i = 0; i < recipeIngredients.size(); i++) {
            ingredients.append("\n");
            ingredients.append(i + 1
                    + ". "
                    + recipeIngredients.get(i).getIngredient()
                    + " ("
                    + recipeIngredients.get(i).getQuantity()
                    + " "
                    + recipeIngredients.get(i).getMeasure()
                    + ")");
        }
    }

    private void setStepsFragment() {
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setStepsArrayList(recipeSteps);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.steps_container, stepsFragment)
                .commit();
    }

    private void setStepDetailsFragment() {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        stepDetailsFragment.setStepsArrayList(recipeSteps);
        stepDetailsFragment.setStepIndex(0);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_details_container, stepDetailsFragment)
                .commit();
    }

    @Override
    public void onStepSelected(int position) {
        if (mTwoPane) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsArrayList(recipeSteps);
            stepDetailsFragment.setStepIndex(position);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.step_details_container, stepDetailsFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(MainActivity.STEPS_INTENT, (Serializable) recipeSteps);
            intent.putExtra(STEP_POSITION, position);
            startActivity(intent);
        }
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
