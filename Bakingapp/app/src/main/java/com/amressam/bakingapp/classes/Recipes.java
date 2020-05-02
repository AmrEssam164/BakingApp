package com.amressam.bakingapp.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Recipes")
public class Recipes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipe_name;
    private String servings;

    @Ignore
    public Recipes(String recipe_name, String servings) {
        this.recipe_name = recipe_name;
        this.servings = servings;
    }

    public Recipes(int id, String recipe_name, String servings) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }
}
