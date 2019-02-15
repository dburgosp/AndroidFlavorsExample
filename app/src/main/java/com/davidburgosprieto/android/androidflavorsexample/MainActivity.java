package com.davidburgosprieto.android.androidflavorsexample;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.davidburgosprieto.android.androidflavorsexample.Ad;
import com.davidburgosprieto.android.androidflavorsexample.utils.MyPreferences;

import static com.davidburgosprieto.android.androidflavorsexample.Utils.Constants.*;

public class MainActivity extends AppCompatActivity {

    // Member variables.
    private MyPreferences mPreferences;
    private Ad mAd;
    private CardView mAdMobCardView, mAdColonyCardView, mFBAudienceCardView;
    private TextView mAdMobTextView, mAdColonyTextView, mFBAudienceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPreferences();
        getLayoutElements();
        getNetworkSatus();
        registerAdNetwork();
        setListeners();
    }

    /* ************** */
    /* Helper methods */
    /* ************** */

    /**
     * Initialises the global SharedPrefences object.
     */
    private void initPreferences() {
        mPreferences = new MyPreferences(this);
    }

    /**
     * Gets all the elements in the layout of this activity.
     */
    private void getLayoutElements() {
        mAdMobCardView = findViewById(R.id.admob_cardview);
        mAdColonyCardView = findViewById(R.id.adcolony_cardview);
        mFBAudienceCardView = findViewById(R.id.fbaudience_cardview);
        mAdMobTextView = findViewById(R.id.admob_textview);
        mAdColonyTextView = findViewById(R.id.adcolony_textview);
        mFBAudienceTextView = findViewById(R.id.fbaudience_textview);
    }

    /**
     * Initialises the TextView with the status of every network.
     */
    private void getNetworkSatus() {
        switch (BUILD_TYPE) {
            case 1: // AdColony.
                showRegistered(mAdColonyCardView, mAdColonyTextView);
                showUnregistered(mAdMobCardView, mAdMobTextView, getStatus(mPreferences.getAdMobStatus()));
                showUnregistered(mFBAudienceCardView, mFBAudienceTextView, getStatus(mPreferences.getFBAudienceStatus()));
                break;

            case 2: // AdMob.
                showUnregistered(mAdColonyCardView, mAdColonyTextView, getStatus(mPreferences.getAdColonyStatus()));
                showRegistered(mAdMobCardView, mAdMobTextView);
                showUnregistered(mFBAudienceCardView, mFBAudienceTextView, getStatus(mPreferences.getFBAudienceStatus()));
                break;

            case 3: // FBAudience.
                showUnregistered(mAdColonyCardView, mAdColonyTextView, getStatus(mPreferences.getAdColonyStatus()));
                showUnregistered(mAdMobCardView, mAdMobTextView, getStatus(mPreferences.getAdMobStatus()));
                showRegistered(mFBAudienceCardView, mFBAudienceTextView);
        }
    }

    /**
     * Translates status string.
     *
     * @param status is the status string fetched from SharedPrefences.
     * @return a "not registered yet" string if status is null, or a "Last connection: status"
     * string otherwise.
     */
    private String getStatus(String status) {
        if (status.equals("")) {
            return getString(R.string.no_network_data);
        } else {
            return getString(R.string.network_data, status);
        }
    }

    /**
     * Sets CardView and TextView to the "registered" status.
     *
     * @param cardView is the CardView object.
     * @param textView is the TextView object.
     */
    private void showRegistered(CardView cardView, TextView textView) {
        // Writes "Registered" String using bold text style.
        textView.setText(getString(R.string.registered));
        textView.setTypeface(null, Typeface.BOLD);

        // Sets CardView alpha to 1, so it looks totally opaque.
        cardView.setAlpha(1.0f);
    }

    /**
     * Sets CardView and TextView to the "no registered" status.
     *
     * @param cardView is the CardView object.
     * @param textView is the TextView object.
     * @param status   is the status string to be shown in the TextView.
     */
    private void showUnregistered(CardView cardView, TextView textView, String status) {
        // Writes status String using normal text style.
        textView.setText(status);
        textView.setTypeface(null, Typeface.NORMAL);

        // Sets CardView alpha to 0.5, so it looks partially transparent.
        cardView.setAlpha(0.5f);
    }

    /**
     * Register to the current ad network and updates the registration status.
     */
    private void registerAdNetwork() {
        // Initialise ad network and create a new interstitial objetc.
        mAd = new Ad(this);
        mAd.createInterstitialAd();

        // Save network registration status in preferences depending in the BUILD_TYPE constant,
        // given by the current compilation flavor.
        switch (BUILD_TYPE) {
            case 1: // AdColony.
                mPreferences.setAdColonyStatus();
                break;

            case 2: // AdMob.
                mPreferences.setAdMobStatus();
                break;

            case 3: // FBAudience.
                mPreferences.setFBAudienceStatus();
        }
    }

    // BORRAR. ES SÓLO PARA PROBAR.
    private void setListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAd.showInterstitialAd();
            }
        };

        switch (BUILD_TYPE) {
            case 1:
                mAdMobCardView.setOnClickListener(listener);
                break;

            case 2:
                mAdMobCardView.setOnClickListener(listener);
                break;

            case 3:
                mAdMobCardView.setOnClickListener(listener);
        }
    }


/*    private void setFBAudience() {
        // Create the interstitial unit with a placement ID (generate your own on the Facebook app settings).
        // Use different ID for each ad placement in your app.
        mInterstitialAd = new InterstitialAd(
                this,
                "YOUR_PLACEMENT_ID");

        // Set a listener to get notified on changes or when the user interact with the ad.
        //mInterstitialAd.setAdListener(this);

        // Load a new interstitial.
        mInterstitialAd.loadAd(EnumSet.of(CacheFlag.VIDEO));
    }*/
}
