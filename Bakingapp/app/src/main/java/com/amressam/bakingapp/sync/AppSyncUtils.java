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

public class AppSyncUtils {

//    private static final int SYNC_INTERVAL_HOURS = 12;
//    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
//    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
//
//    private static final String MOVIES_SYNC_TAG = "movies-sync";

    private static final String TAG = "AppSyncUtils";
    private static boolean sInitialized;


    synchronized public static void initialize(@NonNull final Context context) {
        Log.d(TAG, "initialize: bd2na initialize");
        if (sInitialized) 
        {
            Log.d(TAG, "initialize: already downloaded");
            return;
        }

        Log.d(TAG, "initialize: first time");
        sInitialized = true;
        startImmediateSyncForBakingData(context);


    }

    public static void startImmediateSyncForBakingData(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, BakingDataSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

}