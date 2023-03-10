package com.example.coffeetrail.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopListActivity;

public class MakePostFragment extends Fragment implements View.OnClickListener{
    private Button mPostButton;
    private TextView mPostContent;
    private String currentPost;

    private EditText mPost;
    private CoffeeShopViewModel mShopViewModel;

    private String currentUser;
    private String currentStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_makepost, container, false);
        mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);
        Bundle bundle = this.getArguments();
        if(bundle.getString("shop") != null){
            currentStore = bundle.get("shop").toString();
        }
        if(bundle.getString("userId") != null){
            currentUser = bundle.get("userId").toString();
        }
        if(bundle.get("postContent") != null){
            currentPost = bundle.get("desc").toString();
        }
        mPostButton = v.findViewById(R.id.post_button);
        mPostContent = v.findViewById(R.id.post_text);
        //mPostContent.setText(mShopViewModel.getStoreName(currentStore));
        mPost = v.findViewById(R.id.post_edit);
        mPostButton.setOnClickListener(this);
        return v;
    }
    public ShopOrder createShopOrder(){
        FragmentActivity activity = requireActivity();
        final String post = mPost.getText().toString();
        //need to access current shop and user to make a new shopOrder entry in the database...
        ShopOrder shopOrder = new ShopOrder(post, currentUser, currentStore);
        //mShopOrderViewModel.insert(shopOrder);
        Toast.makeText(activity.getApplicationContext(), "New post added", Toast.LENGTH_SHORT).show();
        return shopOrder;
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.post_button) {
            ShopOrder newOrder = createShopOrder();
            String postContent = newOrder.getDesc();

            Bundle bundle = new Bundle();
            bundle.putString("userId", currentUser);
            bundle.putString("shop", currentStore);
            bundle.putString("postContent", postContent);

            ShopListFragment listFragment = new ShopListFragment();
            listFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).addToBackStack(null).commit();
        }
    }
}