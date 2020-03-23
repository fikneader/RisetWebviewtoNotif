package online.botika.risetwebviewtonotif.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private static SharedPreference mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "RunBackground";
    private static final String KEY_STATUS_SERVICE = "keystatusservice";

    private SharedPreference(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreference(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void statusService(StatusService statusService) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_STATUS_SERVICE, statusService.getStatus());
        editor.apply();
    }

    //this method will give the logged in user
    public StatusService getStatusService() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new StatusService(
                sharedPreferences.getString(KEY_STATUS_SERVICE, null)
        );
    }
}
