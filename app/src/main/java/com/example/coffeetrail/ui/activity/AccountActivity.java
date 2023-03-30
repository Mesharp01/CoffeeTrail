package com.example.coffeetrail.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.coffeetrail.R;
import com.example.coffeetrail.ui.fragment.AccountFragment;
import com.example.coffeetrail.ui.fragment.ShopListFragment;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new AccountFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();

        System.out.println(Double.parseDouble(configurationInfo.getGlEsVersion()));
        System.out.println(configurationInfo.reqGlEsVersion >= 0x30000);
        System.err.println(String.format("%X", configurationInfo.reqGlEsVersion));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}