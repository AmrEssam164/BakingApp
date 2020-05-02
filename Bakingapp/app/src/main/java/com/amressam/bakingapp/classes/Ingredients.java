package com.amressam.bakingapp.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Ingredients")
public class Ingredients implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipe_name;
    private String quantity;
    private String measure;
    private String ingredient;

    @Ignore
    public Ingredients(String recipe_name, String quantity, String measure, String ingredient) {
        this.recipe_name = recipe_name;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredients(int id, String recipe_name, String quantity, String measure, String ingredient) {
        this.id = id;
        this.recipe_name = recipe_name;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
