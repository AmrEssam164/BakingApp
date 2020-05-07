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
    private String widget;

    @Ignore
    public Recipes(String recipe_name, String servings,String widget) {
        this.recipe_name = recipe_name;
        this.servings = servings;
        this.widget=widget;
    }

    public Recipes(int id, String recipe_name, String servings,String widget) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.servings = servings;
        this.widget=widget;
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

    public String getWidget() {
        return widget;
    }

    public void setWidget(String widget) {
        this.widget = widget;
    }
}
