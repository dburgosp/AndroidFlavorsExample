package com.davidburgosprieto.android.androidflavorsexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    final String javaSetFlavourMessage = java.com.davidburgosprieto.android.androidflavorsexample.Utils.Constants.BUILD_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
