package com.amressam.bakingapp.utilities;

import android.util.Log;

import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Pair;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchJsonUtils {

    private static final String TAG = "FetchJsonData";

    public static Map<ArrayList<Recipes>,Pair<ArrayList<Ingredients>, ArrayList<Steps>>> getJson(String data) {
        if (data != null) {
            try {
                ArrayList<Recipes> recipesList = new ArrayList<Recipes>();
                ArrayList<Ingredients> ingredientsList = new ArrayList<Ingredients>();
                ArrayList<Steps> stepsList = new ArrayList<Steps>();
                JSONArray itemsArray = new JSONArray(data);
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonBaking = itemsArray.getJSONObject(i);
                    String recipe_name = jsonBaking.getString("name");
                    JSONArray ingredientsArray = jsonBaking.getJSONArray("ingredients");
                    for (int j = 0; j < ingredientsArray.length(); j++) {
                        JSONObject jsonIngredients = ingredientsArray.getJSONObject(j);
                        String quantity = jsonIngredients.getString("quantity");
                        String measure = jsonIngredients.getString("measure");
                        String ingredient = jsonIngredients.getString("ingredient");
                        Ingredients ingredients = new Ingredients(recipe_name,quantity,measure,ingredient);
                        ingredientsList.add(ingredients);
                    }
                    JSONArray stepsArray = jsonBaking.getJSONArray("steps");
                    for (int j = 0; j < stepsArray.length(); j++) {
                        JSONObject jsonSteps = stepsArray.getJSONObject(j);
                        String shortDescription = jsonSteps.getString("shortDescription");
                        String description = jsonSteps.getString("description");
                        String videoURL = jsonSteps.getString("videoURL");
                        Steps steps = new Steps(recipe_name,shortDescription,description,videoURL);
                        stepsList.add(steps);
                    }
                    String servings = jsonBaking.getString("servings");
                    Recipes recipes = new Recipes(recipe_name,servings);
                    recipesList.add(recipes);
                }
                Pair pair = new Pair<ArrayList<Ingredients>, ArrayList<Steps>>(ingredientsList,stepsList);
                Map<ArrayList<Recipes>,Pair<ArrayList<Ingredients>, ArrayList<Steps>>> map =
                        new HashMap<ArrayList<Recipes>,Pair<ArrayList<Ingredients>, ArrayList<Steps>>>();
                map.put(recipesList,pair);
                return map;
            } catch (JSONException E) {
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }
}
