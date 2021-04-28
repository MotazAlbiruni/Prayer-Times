package com.motazalbiruni.prayertimes.ui;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;

import com.motazalbiruni.prayertimes.R;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    MediaPlayer player = null;
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.motazalbiruni.prayertimes.ui.action.FOO";
    public static final String ACTION_BAZ = "com.motazalbiruni.prayertimes.ui.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.motazalbiruni.prayertimes.ui.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.motazalbiruni.prayertimes.ui.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            player = MediaPlayer.create(this, R.raw.azan);
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)){
                player.start();
            }else {
               stopSelf();
            }
        }
    }//end onHandleIntent()

}//end service