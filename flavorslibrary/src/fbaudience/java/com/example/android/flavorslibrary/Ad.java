package com.example.android.flavorslibrary;

import android.content.Context;
import android.widget.Toast;

import com.facebook.ads.CacheFlag;
import com.facebook.ads.InterstitialAd;

import java.util.EnumSet;

public class Ad {
    // Member variables.
    private Context mContext;
    private InterstitialAd mInterstitialAd;

    /**
     * Constructor for objects of this class.
     *
     * @param context is the Context of the calling activity.
     */
    public Ad(Context context) {
        mContext = context;

        // Creates an interstitial ad object.
        mInterstitialAd = new InterstitialAd(mContext, "YOUR_PLACEMENT_ID");
        mInterstitialAd.loadAd(EnumSet.of(CacheFlag.VIDEO));
    }

    /**
     * Shows the interstitial ad.
     */
    public void showInterstitialAd() {
        if (mInterstitialAd != null && mInterstitialAd.isAdLoaded()) {
            // Show ad if it is already loaded.
            mInterstitialAd.show();
        } else {
            // Display a message if the ad is not yet loaded.
            String msg = mContext.getString(R.string.not_loaded);
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
