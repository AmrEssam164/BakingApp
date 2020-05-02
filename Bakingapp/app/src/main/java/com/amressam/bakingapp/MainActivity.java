package com.amressam.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amressam.bakingapp.adapters.RecipesRecyclerViewAdapter;
import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.database.AppDatabase;
import com.amressam.bakingapp.database.MainViewModel;
import com.amressam.bakingapp.databinding.ActivityMainBinding;
import com.amressam.bakingapp.sync.AppSyncUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipesRecyclerViewAdapter.AdapterOnClickHandler{

    private static final String TAG = "MainActivity";
    private AppDatabase appDB;
    private List<Recipes> mRecipes;
    private List<Ingredients> mIngredients;
    private List<Steps> mSteps;
    ActivityMainBinding mActivityMainBinding;
    RecipesRecyclerViewAdapter mRecipesRecyclerViewAdapter;
    public static final String RECIPES_STATE = "recipe";
    public static final String INGREDIENTS_STATE = "ingredients";
    public static final String STEPS_STATE = "steps";
    public static final String INGREDIENTS_INTENT = "ingredients";
    public static final String STEPS_INTENT = "steps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        AppSyncUtils.initialize(this);
        getSupportActionBar().setTitle("Baking App");
        appDB = AppDatabase.getInstance(this);
        mRecipesRecyclerViewAdapter = new RecipesRecyclerViewAdapter(new ArrayList<Recipes>(),new ArrayList<Steps>(),this,this);
        mActivityMainBinding.recipesRecyclerView.setAdapter(mRecipesRecyclerViewAdapter);
        setUpViewModel();
    }


    @Override
    public void onRecipeClick(final int position) {
        ArrayList<Ingredients> recipeIngredients = new ArrayList<Ingredients>();
        ArrayList<Steps> recipeSteps = new ArrayList<Steps>();
        for(int i=0;i<mIngredients.size();i++){
            if(mRecipes.get(position).getRecipe_name().equals(mIngredients.get(i).getRecipe_name())){
                Ingredients current_ingredient = mIngredients.get(i);
                recipeIngredients.add(current_ingredient);
            }
        }
        for(int i=0;i<mSteps.size();i++){
            if(mRecipes.get(position).getRecipe_name().equals(mSteps.get(i).getRecipe_name())){
                Steps current_step = mSteps.get(i);
                recipeSteps.add(current_step);
            }
        }
        Intent intent = new Intent(this,IngredientsStepsActivity.class);
        intent.putExtra(INGREDIENTS_INTENT, (Serializable) recipeIngredients);
        intent.putExtra(STEPS_INTENT, (Serializable) recipeSteps);
        startActivity(intent);
    }

    private void setUpViewModel(){
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getLiveRecipes().observe(this, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {
                Log.d(TAG, "onChanged: hia mra w7da bs");
                mRecipes=recipes;
                mRecipesRecyclerViewAdapter.loadNewRecipesData(recipes);
            }
        });
        mainViewModel.getLiveIngredients().observe(this, new Observer<List<Ingredients>>() {
            @Override
            public void onChanged(List<Ingredients> ingredients) {
                mIngredients=ingredients;
            }
        });
        mainViewModel.getLiveSteps().observe(this, new Observer<List<Steps>>() {
            @Override
            public void onChanged(List<Steps> steps) {
                mSteps=steps;
                mRecipesRecyclerViewAdapter.loadNewStepsData(steps);
            }
        });
    }
}
