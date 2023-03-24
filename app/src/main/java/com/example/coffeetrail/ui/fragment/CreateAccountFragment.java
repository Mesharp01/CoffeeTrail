package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.UserAccount;
import com.example.coffeetrail.model.UserAccountViewModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button mCancelButton, mCreateAccountButton;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmP;
    private UserAccountViewModel mUserAccountViewModel;
    private final List<UserAccount> mUserAccountList = new CopyOnWriteArrayList<>();


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_account, container, false);
        mUsername = v.findViewById(R.id.username_text);
        mPassword = v.findViewById(R.id.password_text);
        mConfirmP = v.findViewById(R.id.confirm_text);
        mCancelButton = v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
        mCreateAccountButton = v.findViewById(R.id.create_account_button);
        mCreateAccountButton.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v){
        final int viewId = v.getId();
        if (viewId == R.id.create_account_button) {
            createAccount();
        } else if(viewId == R.id.cancel_button){
            returnToLogin();
        }
    }
    private void returnToLogin(){
        FragmentActivity activity = requireActivity();
        activity.getSupportFragmentManager().popBackStack();
    }
    private void createAccount(){
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        FragmentActivity activity = requireActivity();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            if (mPassword.getText().toString().equals(mConfirmP.getText().toString())){
                String hash = hashPassword(password);
                UserAccount newUser = new UserAccount(username, hash);
                if(!mUserAccountList.contains(newUser)) {
                    mUserAccountViewModel.insert(newUser);
                }
                Toast.makeText(activity.getApplicationContext(), "New UserAccount added", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> returnToLogin(), 500);
            }
            else{
                Toast.makeText(activity.getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();

        }
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
}