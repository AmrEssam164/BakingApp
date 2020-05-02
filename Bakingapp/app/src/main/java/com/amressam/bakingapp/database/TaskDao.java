package com.amressam.bakingapp.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Recipes")
    LiveData<List<Recipes>> loadAllRecipes();

    @Query("DELETE FROM Recipes")
    void deleteAllRecipes();

    @Insert
    void insertRecipe(List<Recipes> recipesEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipes recipeEntry);

    @Delete
    void deleteRecipe(Recipes recipeEntry);

    @Query("SELECT * FROM Ingredients")
    LiveData<List<Ingredients>> loadAllIngredients();

    @Query("DELETE FROM Ingredients")
    void deleteAllIngredients();

    @Insert
    void insertIngredient(List<Ingredients> ingredientsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(Ingredients ingredientEntry);

    @Delete
    void deleteIngredient(Ingredients ingredientEntry);

    @Query("SELECT * FROM Steps")
    LiveData<List<Steps>> loadAllSteps();

    @Query("DELETE FROM Steps")
    void deleteAllSteps();

    @Insert
    void insertStep(List<Steps> stepsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(Steps stepEntry);

    @Delete
    void deleteStep(Steps stepEntry);
}
