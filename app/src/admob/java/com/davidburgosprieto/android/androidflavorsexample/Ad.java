package com.davidburgosprieto.android.androidflavorsexample;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Ad {
    // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
    private String APP_ID = "ca-app-pub-3940256099942544~3347511713";

    // Sample AdMob block ID: ca-app-pub-3940256099942544/1033173712
    private String BLOCK_ID = "ca-app-pub-3940256099942544/1033173712";

    // Member variables.
    private Context mContext;
    private InterstitialAd mInterstitialAd;

    /**
     * Constructor for objects of this class.
     *
     * @param context is the Context of the calling activity.
     */
    public Ad(Context context) {
        MobileAds.initialize(context, APP_ID);
        mContext = context;
    }

    /**
     * Creates an interstitial ad object.
     */
    public void createInterstitialAd() {
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(BLOCK_ID);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Shows the interstitial ad.
     */
    public void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
