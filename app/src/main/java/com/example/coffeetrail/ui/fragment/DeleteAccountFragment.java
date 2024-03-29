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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteAccountFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button mCancelButton, mChangePasswordButton, mDeleteAccountButton;
    private EditText mUsername;
    private EditText mPassword;
    private UserAccountViewModel mUserAccountViewModel;
    private final List<UserAccount> mUserAccountList = new CopyOnWriteArrayList<>();
    public DeleteAccountFragment() {
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
        View v = inflater.inflate(R.layout.fragment_delete_account, container, false);
        mUsername = v.findViewById(R.id.username_text);
        mPassword = v.findViewById(R.id.password_text);
        mCancelButton = v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(this);
        mDeleteAccountButton = v.findViewById(R.id.delete_account_button);
        mDeleteAccountButton.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v){
        final int viewId = v.getId();
        if (viewId == R.id.delete_account_button) {
            deleteAccount();
        } else if(viewId == R.id.cancel_button){
            returnToLogin();
        }
    }
    private void returnToLogin(){
        FragmentActivity activity = requireActivity();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().popBackStack();
    }
    private void deleteAccount(){
        final String username = mUsername.getText().toString();
        final String password = mPassword.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            UserAccount newUser = new UserAccount(username, password);
            if(mUserAccountList.contains(newUser));
            mUserAccountViewModel.deleteUser(username, password);
        }
        FragmentActivity activity = requireActivity();
        Toast.makeText(activity.getApplicationContext(), "User Account deleted", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> returnToLogin(), 1000);
    }

}