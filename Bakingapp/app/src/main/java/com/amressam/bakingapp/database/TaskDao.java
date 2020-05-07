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

    @Query("SELECT * FROM Recipes WHERE "+
            "widget = :widget")
    List<Recipes> loadSpecificRecipes(String widget);

    @Query("DELETE FROM Recipes")
    void deleteAllRecipes();

    @Query("SELECT * FROM Ingredients")
    LiveData<List<Ingredients>> loadAllIngredients();

    @Query("SELECT * FROM Ingredients WHERE "+
            "recipe_name = :recipeName")
    List<Ingredients> loadSpecificIngredients(String recipeName);

    @Query("DELETE FROM Ingredients")
    void deleteAllIngredients();

    @Query("SELECT * FROM Steps")
    LiveData<List<Steps>> loadAllSteps();

    @Query("SELECT * FROM Steps WHERE "+
            "recipe_name = :recipeName")
    List<Steps> loadSpecificSteps(String recipeName);

    @Query("DELETE FROM Steps")
    void deleteAllSteps();


    @Insert
    void insertRecipe(List<Recipes> recipesEntry);

    @Insert
    void insertIngredient(List<Ingredients> ingredientsEntry);

    @Insert
    void insertStep(List<Steps> stepsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipes recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(Ingredients ingredientEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(Steps stepEntry);

    @Delete
    void deleteRecipe(Recipes recipeEntry);

    @Delete
    void deleteIngredient(Ingredients ingredientEntry);

    @Delete
    void deleteStep(Steps stepEntry);
}
