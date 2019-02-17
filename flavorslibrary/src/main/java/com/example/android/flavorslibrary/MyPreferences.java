package com.example.android.flavorslibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.example.android.flavorslibrary.MyBuildTypes.BUILD_TYPE_ADCOLONY;
import static com.example.android.flavorslibrary.MyBuildTypes.BUILD_TYPE_ADMOB;
import static com.example.android.flavorslibrary.MyBuildTypes.BUILD_TYPE_FBAUDIENCE;
import static com.example.android.flavorslibrary.MyDateTime.getStringCurrentDate;

public class MyPreferences {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private SharedPreferences mSharedPreferences;
    private Resources mResources;

    /* ************ */
    /* Constructors */
    /* ************ */

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
     * Fetches SharedPreferences for the last saved network connection status, given by the build
     * type parameter.
     *
     * @param buildType is the current build type that determines the current ad network.
     * @return a string containing the last saved status of the connection to the FBAudience
     * network.
     */
    public String getNetworkStatus(int buildType) {
        return mSharedPreferences.getString(getKey(buildType), "");
    }

    /**
     * Public method to update the current network connection status, given by the build type
     * parameter, to the current date and time.
     *
     * @param buildType is the current build type that determines the current ad network.
     */
    public void setNetworkStatus(int buildType) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(getKey(buildType), getStringCurrentDate());
        editor.apply();
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Returns the corresponding SharedPreferences key depending on the build type parameter.
     *
     * @param buildType is the current build type that determines the current ad network.
     * @return a String SharedPreferences key.
     */
    private String getKey(int buildType) {
        switch (buildType) {
            case BUILD_TYPE_ADCOLONY:
                return mResources.getString(R.string.preferences_adcolony_status_key);

            case BUILD_TYPE_ADMOB:
                return mResources.getString(R.string.preferences_admob_status_key);

            case BUILD_TYPE_FBAUDIENCE:
            default:
                return mResources.getString(R.string.preferences_fbaudience_status_key);
        }
    }
}
