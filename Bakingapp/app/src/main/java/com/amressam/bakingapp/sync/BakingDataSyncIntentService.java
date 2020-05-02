package com.amressam.bakingapp.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BakingDataSyncIntentService extends IntentService {
    public BakingDataSyncIntentService() {
        super("BakingDataSyncIntentService");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onHandleIntent(Intent intent) {
        AppSyncTask.syncBakingData(this);
    }
}
