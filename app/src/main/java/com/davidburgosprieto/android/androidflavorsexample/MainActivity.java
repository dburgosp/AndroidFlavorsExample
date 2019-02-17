package com.davidburgosprieto.android.androidflavorsexample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.davidburgosprieto.android.androidflavorsexample.utils.MyPreferences;

import static com.davidburgosprieto.android.androidflavorsexample.Constants.BUILD_TYPE;
import static com.davidburgosprieto.android.androidflavorsexample.utils.MyBuildTypes.BUILD_TYPE_ADCOLONY;
import static com.davidburgosprieto.android.androidflavorsexample.utils.MyBuildTypes.BUILD_TYPE_ADMOB;
import static com.davidburgosprieto.android.androidflavorsexample.utils.MyBuildTypes.BUILD_TYPE_FBAUDIENCE;

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
     * Initialises the global SharedPreferences object.
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
        // Get custom network status strings, depending on the values stored on SharedPreferences.
        String adcolonyStatus = getStatus(mPreferences.getNetworkStatus(BUILD_TYPE_ADCOLONY));
        String admobStatus = getStatus(mPreferences.getNetworkStatus(BUILD_TYPE_ADMOB));
        String fbaudienceStatus = getStatus(mPreferences.getNetworkStatus(BUILD_TYPE_FBAUDIENCE));

        // Display the corresponding status strings, depending on the current build type.
        switch (BUILD_TYPE) {
            case BUILD_TYPE_ADCOLONY:
                showRegistered(mAdColonyCardView, mAdColonyTextView);
                showUnregistered(mAdMobCardView, mAdMobTextView, admobStatus);
                showUnregistered(mFBAudienceCardView, mFBAudienceTextView, fbaudienceStatus);
                break;

            case BUILD_TYPE_ADMOB:
                showUnregistered(mAdColonyCardView, mAdColonyTextView, adcolonyStatus);
                showRegistered(mAdMobCardView, mAdMobTextView);
                showUnregistered(mFBAudienceCardView, mFBAudienceTextView, fbaudienceStatus);
                break;

            case BUILD_TYPE_FBAUDIENCE:
                showUnregistered(mAdColonyCardView, mAdColonyTextView, adcolonyStatus);
                showUnregistered(mAdMobCardView, mAdMobTextView, admobStatus);
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
        String customStatus;
        if (status.equals("")) {
            customStatus = getString(R.string.no_network_data);
        } else {
            customStatus = getString(R.string.network_data, status);
        }
        return customStatus + '\n' + getString(R.string.click_to_save);
    }

    /**
     * Sets CardView and TextView to the "registered" status.
     *
     * @param cardView is the CardView object.
     * @param textView is the TextView object.
     */
    private void showRegistered(CardView cardView, TextView textView) {
        // Writes "Registered" String using bold text style.
        String status = getString(R.string.registered) + '\n' + getString(R.string.click_to_save);
        textView.setText(status);
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
     * Register to the current ad network.
     */
    private void registerAdNetwork() {
        // Initialise ad network and create a new interstitial object.
        mAd = new Ad(this);
    }

    /**
     * Add listeners to every CardView depending on the build type. When user clicks on the CardView
     * related to the current build type, save network status and show a "Registered" message;
     * otherwise, don't save status and show a "Not registered" message.
     */
    private void setListeners() {
        // Listener to add to the CardView related to the current build type.
        View.OnClickListener registeredNetworkListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save current status network.
                mPreferences.setNetworkStatus(BUILD_TYPE);
                Toast.makeText(MainActivity.this, R.string.toast_registered, Toast.LENGTH_SHORT).show();

                // For testing only.
                mAd.showInterstitialAd();
            }
        };

        // Listener to add to the CardViews that are not related to the current build type.
        View.OnClickListener notRegisteredNetworkListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Raise a "Not registered" message.
                Toast.makeText(MainActivity.this, R.string.toast_not_registered, Toast.LENGTH_SHORT).show();
            }
        };

        // Add the corresponding listeners depending on the build type.
        switch (BUILD_TYPE) {
            case BUILD_TYPE_ADCOLONY:
                mAdColonyCardView.setOnClickListener(registeredNetworkListener);
                mAdMobCardView.setOnClickListener(notRegisteredNetworkListener);
                mFBAudienceCardView.setOnClickListener(notRegisteredNetworkListener);
                break;

            case BUILD_TYPE_ADMOB:
                mAdColonyCardView.setOnClickListener(notRegisteredNetworkListener);
                mAdMobCardView.setOnClickListener(registeredNetworkListener);
                mFBAudienceCardView.setOnClickListener(notRegisteredNetworkListener);
                break;

            case BUILD_TYPE_FBAUDIENCE:
                mAdColonyCardView.setOnClickListener(notRegisteredNetworkListener);
                mAdMobCardView.setOnClickListener(notRegisteredNetworkListener);
                mFBAudienceCardView.setOnClickListener(registeredNetworkListener);
        }
    }
}
