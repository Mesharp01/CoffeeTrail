package com.example.coffeetrail.ui.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
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

public class ModifyPostFragment extends Fragment implements View.OnClickListener  {
    private ShopOrder mPost;
    private EditText mPostContent;
    private Button mModifyButton;
    private Button mDeleteButton;
    private ShopOrderViewModel mShopOrderViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_modify_post, container, false);
        //mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);
        Bundle bundle = this.getArguments();


        mPostContent = v.findViewById(R.id.post_content);
        if(bundle.get("order") != null){
            mPost = (ShopOrder) bundle.get("order");

            mPostContent.setHint(mPost.getDesc());
        }
        mModifyButton = v.findViewById(R.id.modify_post_button);
        mDeleteButton = v.findViewById(R.id.delete_post_button);


        mModifyButton.setOnClickListener(this);
        mDeleteButton.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.modify_post_button) {
            mShopOrderViewModel.updatePostDesc(mPostContent.getText().toString(), mPost.oid);
            new Handler().postDelayed(() -> returnToShopOrders(), 500);
        } else if (viewId == R.id.delete_post_button) {
            FragmentActivity activity = requireActivity();
            Toast.makeText(activity.getApplicationContext(), "Post deleted", Toast.LENGTH_SHORT).show();
            mShopOrderViewModel.delete(mPost);
            new Handler().postDelayed(() -> returnToShopOrders(), 500);
        }
    }

    private void returnToShopOrders(){
        FragmentActivity activity = requireActivity();
        activity.getSupportFragmentManager().popBackStack();
    }
}