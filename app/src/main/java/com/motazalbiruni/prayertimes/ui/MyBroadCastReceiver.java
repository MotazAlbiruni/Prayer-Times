package com.motazalbiruni.prayertimes.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String key1 = intent.getStringExtra("key1");

        Intent intentServiceSound = new Intent(context, MyIntentService.class);
        intentServiceSound.setAction(MyIntentService.ACTION_FOO);
        intentServiceSound.putExtra(MyIntentService.EXTRA_PARAM1, key1);
        context.startService(intentServiceSound);
    }

}//end class