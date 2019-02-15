package com.davidburgosprieto.android.androidflavorsexample.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.davidburgosprieto.android.androidflavorsexample.R;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.davidburgosprieto.android.androidflavorsexample.utils.MyDateTime.getStringCurrentDate;

public class MyPreferences {

    private SharedPreferences mSharedPreferences;
    private Resources mResources;

    /**
     * Constructor for objects of this class.
     *
     * @param context is the context of the calling activity.
     */
    public MyPreferences(Context context) {
        mSharedPreferences = getDefaultSharedPreferences(context);
        mResources = context.getResources();
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Fetches SharedPreferences for the last saved status of the connection to the AdColony
     * network.
     *
     * @return a string containing the last saved status of the connection to the AdColony network.
     */
    public String getAdColonyStatus() {
        String key = mResources.getString(R.string.preferences_adcolony_status_key);
        return mSharedPreferences.getString(key, "");
    }

    /**
     * Fetches SharedPreferences for the last saved status of the connection to the AdMob network.
     *
     * @return a string containing the last saved status of the connection to the AdMob network.
     */
    public String getAdMobStatus() {
        String key = mResources.getString(R.string.preferences_admob_status_key);
        return mSharedPreferences.getString(key, "");
    }

    /**
     * Fetches SharedPreferences for the last saved status of the connection to the FBAudience
     * network.
     *
     * @return a string containing the last saved status of the connection to the FBAudience
     * network.
     */
    public String getFBAudienceStatus() {
        String key = mResources.getString(R.string.preferences_fbaudience_status_key);
        return mSharedPreferences.getString(key, "");
    }

    /**
     * Helper method to update the last saved status of the connection to the AdColony network.
     */
    public void setAdColonyStatus() {
        String key = mResources.getString(R.string.preferences_adcolony_status_key);
        setCurrentStatus(key);
    }

    /**
     * Helper method to update the last saved status of the connection to the AdMob network.
     */
    public void setAdMobStatus() {
        String key = mResources.getString(R.string.preferences_admob_status_key);
        setCurrentStatus(key);
    }

    /**
     * Helper method to update the last saved status of the connection to the FBAudience network.
     */
    public void setFBAudienceStatus() {
        String key = mResources.getString(R.string.preferences_fbaudience_status_key);
        setCurrentStatus(key);
    }

    /* *************** */
    /* Private methods */
    /* *************** */

    /**
     * @param key is the shared preferences key for saving the current status to the current date
     *            and time.
     */
    private void setCurrentStatus(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, getStringCurrentDate());
        editor.apply();
    }
}
