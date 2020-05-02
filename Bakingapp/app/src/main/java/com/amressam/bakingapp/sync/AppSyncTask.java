package com.amressam.bakingapp.sync;

import android.content.Context;
import android.os.Build;
import android.renderscript.Sampler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.amressam.bakingapp.MainActivity;
import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Pair;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.database.AppDatabase;
import com.amressam.bakingapp.database.AppExecutors;
import com.amressam.bakingapp.utilities.FetchJsonUtils;
import com.amressam.bakingapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;
import java.util.function.BiConsumer;

public class AppSyncTask {

    private static final String TAG = "AppSyncTask";

    @RequiresApi(api = Build.VERSION_CODES.N)
    synchronized public static void syncBakingData(Context context) {

        try {
            URL bakingDataRequestUrl = NetworkUtils.buildBakingUrl();

            String jsonBakingDataResponse = NetworkUtils.getResponseFromHttpUrl(bakingDataRequestUrl);

            Map<ArrayList<Recipes>, Pair<ArrayList<Ingredients>, ArrayList<Steps>>> map = FetchJsonUtils.getJson(jsonBakingDataResponse);
            ArrayList<Recipes> recipes = new ArrayList<Recipes>();
            ArrayList<Ingredients> ingredients = new ArrayList<Ingredients>();
            ArrayList<Steps> steps = new ArrayList<Steps>();
            Pair<ArrayList<Ingredients>, ArrayList<Steps>> pair = new Pair<ArrayList<Ingredients>, ArrayList<Steps>>();
            for (Map.Entry<ArrayList<Recipes>, Pair<ArrayList<Ingredients>, ArrayList<Steps>>> entry : map.entrySet()) {
                recipes = entry.getKey();
                pair = entry.getValue();
            }
            ingredients = pair.getL();
            steps = pair.getR();
            int recipes_count = recipes.size();
            int ingredients_count = ingredients.size();
            int steps_count = steps.size();
            Log.d(TAG, "syncBakingData: ==================== " + recipes_count + " " + ingredients_count + " " + steps_count);
            final ArrayList<Recipes> recipes_temp=recipes;
            final ArrayList<Ingredients> ingredients_temp=ingredients;
            final ArrayList<Steps> steps_temp=steps;
            if (recipes_count != 0 && ingredients_count != 0 && steps_count != 0) {
                AppDatabase appDB = AppDatabase.getInstance(context);
                appDB.taskDao().deleteAllRecipes();
                appDB.taskDao().deleteAllIngredients();
                appDB.taskDao().deleteAllSteps();
                appDB.taskDao().insertRecipe(recipes_temp);
                appDB.taskDao().insertIngredient(ingredients_temp);
                appDB.taskDao().insertStep(steps_temp);
//                AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
