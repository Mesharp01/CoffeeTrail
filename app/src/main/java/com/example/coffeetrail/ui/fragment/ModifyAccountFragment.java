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
 * Use the {@link ModifyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyAccountFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button mCancelButton, mChangePasswordButton, mDeleteAccountButton;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mNewPassword;
    private EditText mNewConfirm;
    private UserAccountViewModel mUserAccountViewModel;
    private final List<UserAccount> mUserAccountList = new CopyOnWriteArrayList<>();
    public ModifyAccountFragment() {
        // Required empty public constructor
    }
    public static ModifyAccountFragment newInstance(String param1, String param2) {
        ModifyAccountFragment fragment = new ModifyAccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
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
        View v = inflater.inflate(R.layout.fragment_modify_account, container, false);
        mUsername = v.findViewById(R.id.username_text);
        mPassword = v.findViewById(R.id.password_text);
        mNewPassword = v.findViewById(R.id.new_password_text);
        mNewConfirm = v.findViewById(R.id.confirm_text);
        mCancelButton = v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
        mChangePasswordButton = v.findViewById(R.id.change_password_button);
        mChangePasswordButton.setOnClickListener(this);
        mDeleteAccountButton = v.findViewById(R.id.delete_account_button);
        mDeleteAccountButton.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v){
        final int viewId = v.getId();
        if (viewId == R.id.change_password_button) {
            changePassword();
        } else if(viewId == R.id.cancel_button){
            returnToLogin();
        } else if(viewId == R.id.delete_account_button){
            deleteAccount();
        }
    }
    private void returnToLogin(){
        FragmentActivity activity = requireActivity();
        activity.getSupportFragmentManager().popBackStack();
    }
    private void changePassword(){
        final String username = mUsername.getText().toString();
        final String password = hashPassword(mPassword.getText().toString());
        final String newPassword = mNewPassword.getText().toString();
        final String newConfirm = mNewConfirm.getText().toString();
        FragmentActivity activity = requireActivity();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(newPassword) && !TextUtils.isEmpty(newConfirm)){
            if (newPassword.equals(newConfirm)){
                UserAccount newUser = new UserAccount(username, password);
                if(mUserAccountList.contains(newUser)) {
                    //this isn't working
                    //ben's changes should fix
                    mUserAccountList.remove(newUser);
                    newUser = new UserAccount(username, hashPassword(newPassword));
                    mUserAccountList.add(newUser);
                    Toast.makeText(activity.getApplicationContext(), "Password updated", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> returnToLogin(), 500);
                } else{
                    Toast.makeText(activity.getApplicationContext(), "Username and password not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity.getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(activity.getApplicationContext(), "Complete all fields", Toast.LENGTH_SHORT).show();

        }



    }
    private void deleteAccount(){
        final String username = mUsername.getText().toString();
        final String password = hashPassword(mPassword.getText().toString());
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            UserAccount newUser = new UserAccount(username, password);
            mUserAccountList.remove(newUser);
        }
        FragmentActivity activity = requireActivity();
        Toast.makeText(activity.getApplicationContext(), "User Account deleted", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> returnToLogin(), 500);
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