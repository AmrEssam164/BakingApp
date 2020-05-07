package com.amressam.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amressam.bakingapp.classes.Ingredients;
import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.classes.Steps;
import com.amressam.bakingapp.database.AppDatabase;
import com.amressam.bakingapp.database.MainViewModel;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "ListRemoteViewsFactory";
    Context mContext;
    ArrayList<Recipes> mRecipeDetails;
    ArrayList<Ingredients> mIngredients;
    ArrayList<Steps> mSteps;
    AppDatabase appDB;
    public static final String INGREDIENTS_INTENT = "ingredients";
    public static final String STEPS_INTENT = "steps";
    public static final String POSITION_INTENT = "position";

    public ListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        // Get all plant info ordered by creation time
        appDB = AppDatabase.getInstance(mContext);
        mIngredients = new ArrayList<Ingredients>();
        mRecipeDetails = new ArrayList<Recipes>();
        mRecipeDetails = (ArrayList<Recipes>) appDB.taskDao().loadSpecificRecipes("true");

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (mRecipeDetails == null) return 0;
        return mRecipeDetails.size();
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the GridView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (mRecipeDetails == null || mRecipeDetails.size() == 0) {
            return null;
        }
        Recipes recipeDetails = mRecipeDetails.get(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget);
        mIngredients = (ArrayList<Ingredients>) appDB.taskDao().loadSpecificIngredients(recipeDetails.getRecipe_name());
        mSteps = (ArrayList<Steps>) appDB.taskDao().loadSpecificSteps(recipeDetails.getRecipe_name());
        String ingredients = "Ingredients of "+recipeDetails.getRecipe_name()+":";
        int index;
        for (int i = 0; i < mIngredients.size(); i++) {
            ingredients += "\n";
            index = i + 1;
            ingredients += index
                    + ". "
                    + mIngredients.get(i).getIngredient()
                    + " ("
                    + mIngredients.get(i).getQuantity()
                    + " "
                    + mIngredients.get(i).getMeasure()
                    + ").";
        }
        views.setTextViewText(R.id.appwidget_text, ingredients);

        Intent intent = new Intent();
        intent.putExtra(INGREDIENTS_INTENT, (Serializable) mIngredients);
        intent.putExtra(STEPS_INTENT, (Serializable) mSteps);
        intent.putExtra(POSITION_INTENT,position);
        views.setOnClickFillInIntent(R.id.appwidget_text,intent);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

