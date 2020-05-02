package com.amressam.bakingapp.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Recipes>> liveRecipes;
    private androidx.lifecycle.LiveData<List<Ingredients>> liveIngredients;
    private LiveData<List<Steps>> liveSteps;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        liveRecipes = appDatabase.taskDao().loadAllRecipes();
        liveIngredients = appDatabase.taskDao().loadAllIngredients();
        liveSteps = appDatabase.taskDao().loadAllSteps();
    }

    public LiveData<List<Recipes>> getLiveRecipes() {
        return liveRecipes;
    }

    public LiveData<List<Ingredients>> getLiveIngredients() {
        return liveIngredients;
    }

    public LiveData<List<Steps>> getLiveSteps() {
        return liveSteps;
    }
}
