package com.example.coffeetrail.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopListActivity;

public class MakePostFragment extends Fragment implements View.OnClickListener{
    private Button mPostButton;
    private TextView mPostContent;

    private EditText mPost;
    private ShopOrderViewModel mShopOrderViewModel;

    private int userId;
    private int shopId;
    private String storeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_makepost, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            storeName = bundle.get("name").toString();
        }
        mPostButton = v.findViewById(R.id.post_button);
        mPostContent = v.findViewById(R.id.post_text);
        mPostContent.setText(storeName);
        mPost = v.findViewById(R.id.post_edit);
        mPostButton.setOnClickListener(this);
        userId = 1;
        shopId = 1;
        return v;
    }
    public ShopOrder createShopOrder(){
        FragmentActivity activity = requireActivity();
        final String post = mPost.getText().toString();
        //need to access current shop and user to make a new shopOrder entry in the database...
        ShopOrder shopOrder = new ShopOrder(post, userId, shopId);
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
            bundle.putString("postContent", postContent);

            ShopOrderFragment orderFragment = new ShopOrderFragment();
            orderFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderFragment).addToBackStack(null).commit();
        }
    }
}