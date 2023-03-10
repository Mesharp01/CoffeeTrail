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
private static final String accountBundleKey = "bundleAccount";


    public CreateAccountFragment() {
        // Required empty public constructor
    }

    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
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
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            UserAccount newUser = new UserAccount(username, password);

            //pass new uid to the fragment arguments to be used later
            Bundle bundle = new Bundle();
            Bundle bundleAccount = new Bundle();
            bundleAccount.putInt("uid", newUser.getUid());
            bundleAccount.putString("name", newUser.getName());
            bundle.putBundle("bundleAccount", bundleAccount);
            ShopListFragment shopListFragment = new ShopListFragment();
            shopListFragment.setArguments(bundle);
            if(!mUserAccountList.contains(newUser)) {
                mUserAccountViewModel.insert(newUser);
            }
        }
        FragmentActivity activity = requireActivity();
        Toast.makeText(activity.getApplicationContext(), "New UserAccount added", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> returnToLogin(), 3000);

    }
}