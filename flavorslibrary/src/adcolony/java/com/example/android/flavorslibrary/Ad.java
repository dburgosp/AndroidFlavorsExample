package com.example.android.flavorslibrary;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;

public class Ad {
    final private String APP_ID = "app185a7e71e1714831a49ec7";
    final private String ZONE_ID = "vz06e8c32a037749699e7050";
    final private String TAG = "AdColonyDemo";

    // Member variables.
    private Context mContext;
    private AdColonyInterstitial mInterstitialAd;
    private AdColonyInterstitialListener mListener;
    private AdColonyAdOptions mAdOptions;

    /**
     * Constructor for objects of this class.
     *
     * @param context is the Context of the calling activity.
     */
    public Ad(Context context) {
        mContext = context;

        // Construct optional app options object to be sent with configure
        AdColonyAppOptions appOptions = new AdColonyAppOptions()
                .setUserID("unique_user_id")
                .setKeepScreenOn(true);

        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
        // be available as soon as possible.
        AdColony.configure((Activity) context, appOptions, APP_ID, ZONE_ID);

        // Optional user metadata sent with the ad options in each request
        AdColonyUserMetadata metadata = new AdColonyUserMetadata()
                .setUserAge(26)
                .setUserEducation(AdColonyUserMetadata.USER_EDUCATION_BACHELORS_DEGREE)
                .setUserGender(AdColonyUserMetadata.USER_MALE);

        // Ad specific options to be sent with request
        mAdOptions = new AdColonyAdOptions().setUserMetadata(metadata);

        // Set up mListener for interstitial ad callbacks. You only need to implement the callbacks
        // that you care about. The only required callback is onRequestFilled, as this is the only
        // way to get an ad object.
        mListener = new AdColonyInterstitialListener() {
            @Override
            public void onRequestFilled(AdColonyInterstitial ad) {
                // Ad passed back in request filled callback, ad can now be shown
                mInterstitialAd = ad;
            }

            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                // Ad request was not filled
            }

            @Override
            public void onOpened(AdColonyInterstitial ad) {
                // Ad opened, reset UI to reflect state change
            }

            @Override
            public void onExpiring(AdColonyInterstitial ad) {
                // Request a new ad if ad is expiring
                AdColony.requestInterstitial(ZONE_ID, this, mAdOptions);
            }
        };
    }

    /**
     * Shows the interstitial ad.
     */
    public void showInterstitialAd() {
        if (mInterstitialAd == null || mInterstitialAd.isExpired()) {
            AdColony.requestInterstitial(ZONE_ID, mListener, mAdOptions);

            // Display a message if the ad is not yet loaded.
            String msg = mContext.getString(R.string.not_loaded);
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        } else {
            // Show ad if it is already loaded.
            mInterstitialAd.show();
        }
    }
}
