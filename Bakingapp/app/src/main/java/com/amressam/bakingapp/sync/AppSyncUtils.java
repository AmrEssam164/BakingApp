/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amressam.bakingapp.sync;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amressam.bakingapp.classes.Recipes;
import com.amressam.bakingapp.database.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppSyncUtils {

    private static final String TAG = "AppSyncUtils";
    private static boolean sInitialized;
    private static ArrayList<Recipes> mRecipes;


    synchronized public static void initialize(@NonNull final Context context) {
        Log.d(TAG, "initialize: bd2na initialize");
        if (sInitialized) 
        {
            Log.d(TAG, "initialize: already downloaded");
            return;
        }

        Log.d(TAG, "initialize: first time");
        sInitialized = true;

        MainViewModel mainViewModel = ViewModelProviders.of((FragmentActivity) context).get(MainViewModel.class);
        mainViewModel.getLiveRecipes().observe((LifecycleOwner) context, new Observer<List<Recipes>>() {
            @Override
            public void onChanged(List<Recipes> recipes) {
                mRecipes=(ArrayList<Recipes>) recipes;
                if(recipes.size()==0){
                    Log.d(TAG, "onChanged: Database is empty");
                    startImmediateSyncForBakingData(context);
                } else {
                    Log.d(TAG, "onChanged: Database isn't empty");
                }
            }
        });
    }

    public static void startImmediateSyncForBakingData(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, BakingDataSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

}