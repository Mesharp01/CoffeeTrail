package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
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
import com.example.coffeetrail.model.UserAccountViewModel;

public class MakePostFragment extends Fragment implements View.OnClickListener {
    private Button mPostButton;
    private TextView mPostContent;
    private ShopOrderViewModel mShopOrderViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_post, container, false);
        mPostButton = v.findViewById(R.id.post_button);
        mPostContent = v.findViewById(R.id.post_content);
        mPostButton.setOnClickListener(this);

        return v;
    }
    @Override
    public void OnClick(View v){
        final int viewId = v.getId();
        if (viewId == R.id.post_button) {
            createShopOrder();
        }
    }
    public void createShopOrder(){
        FragmentActivity activity = requireActivity();
        final String post = mPostContent.getText().toString();
        //need to access current shop and user to make a new shopOrder entry in the database...
        //ShopOrder shopOrder = new ShopOrder(post, );
        //mShopOrderViewModel.insert(shopOrder);
        Toast.makeText(activity.getApplicationContext(), "New post added", Toast.LENGTH_SHORT).show();
    }
}