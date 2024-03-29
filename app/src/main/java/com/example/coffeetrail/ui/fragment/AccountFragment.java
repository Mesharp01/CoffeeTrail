package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.UserAccount;
import com.example.coffeetrail.model.UserAccountViewModel;
import com.example.coffeetrail.ui.activity.ShopListActivity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AccountFragment";
    private Button mLoginButton, mCreateAccountButton, mModifyAccountButton;
    private TextView mCBUS, mCoffeeTrail;
    private EditText mUsername;
    private EditText mPassword;
    private UserAccountViewModel mUserAccountViewModel;
    private final List<UserAccount> mUserAccountList = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        Activity activity = requireActivity();
        mUserAccountViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(UserAccountViewModel.class);
        mUserAccountViewModel.getAllUserAccounts().observe((LifecycleOwner) activity, userAccounts -> {
            mUserAccountList.clear();
            mUserAccountList.addAll(userAccounts);
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        mUsername = v.findViewById(R.id.username_text);
        mPassword = v.findViewById(R.id.password_text);
        mLoginButton = v.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);
        mCreateAccountButton = v.findViewById(R.id.create_account_button);
        mCreateAccountButton.setOnClickListener(this);
        mModifyAccountButton = v.findViewById(R.id.modify_account_button);
        mModifyAccountButton.setOnClickListener(this);
//        mCBUS = v.findViewById(R.id.cbus);
//        mCoffeeTrail = v.findViewById(R.id.coffeetrail);
//        mCBUS.getPaint().setStrokeWidth(5);
//        mCBUS.getPaint().setStyle(Paint.Style.STROKE);
//        mCoffeeTrail.getPaint().setStrokeWidth(5);
//        mCoffeeTrail.getPaint().setStyle(Paint.Style.STROKE);

        return v;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored() called");
    }
    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.login_button) {
            checkLogin(v);
        } else if(viewId == R.id.create_account_button){
            createAccount();
        } else if (viewId == R.id.modify_account_button){
            modifyAccount();
        }
    }
    private void checkLogin(View v){
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String hash = hashPassword(password);
            UserAccount user = new UserAccount(username, hash);
            boolean loginCheck = mUserAccountList.contains(user);
            if (loginCheck) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);

                ShopListFragment shopFragment = new ShopListFragment();
                shopFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, shopFragment).addToBackStack(null).commit();
            } else {
                FragmentActivity activity = requireActivity();
                Toast.makeText(activity.getApplicationContext(), "Username and password not found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void createAccount(){
        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = new CreateAccountFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("create_account_fragment")
                .commit();

    }
    public void modifyAccount(){
        FragmentManager fm = getParentFragmentManager();
        Fragment fragment = new ModifyAccountFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("modify_account_fragment")
                .commit();
    }

    private String hashPassword(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashed);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 does not exist");
        }

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    /*
    LifeCycle Methods
     */
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
        Activity activity = requireActivity();
        mUserAccountViewModel.getAllUserAccounts().observe((LifecycleOwner) activity, userAccounts -> {
            mUserAccountList.clear();
            mUserAccountList.addAll(userAccounts);
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState() called");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


}