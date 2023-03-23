package com.example.coffeetrail.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccount;

public class ShopOrderHolder extends RecyclerView.ViewHolder {
    private final TextView mShopOrderTextView;
    private Button mEditButton;
    private Context mContext;
    private ShopOrder mShopOrder;
    private CoffeeShop currentStore;
    private UserAccount currentUser;
    private ShopOrder currentPost;


    private ShopOrderHolder(Context context, CoffeeShop shop, UserAccount user,View itemView) {
        super(itemView);
        mContext = context;
        currentStore = shop;
        currentUser = user;
        mShopOrderTextView = itemView.findViewById(R.id.list_item_shoporder);
        mEditButton = itemView.findViewById(R.id.edit_post_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyPost();
            }
        });

    }
    public void modifyPost(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("shop", currentStore);
        bundle.putSerializable("user", currentUser);
        bundle.putSerializable("shoporder",  currentPost);

        ShopOrderFragment shopOrderFragment = (ShopOrderFragment) ((AppCompatActivity)mContext).getSupportFragmentManager().findFragmentByTag("SHOP_ORDER_FRAGMENT");

        FragmentManager fm = shopOrderFragment.getParentFragmentManager();
        Fragment fragment = new ModifyPostFragment();
        fragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    void bind(ShopOrder order) {
        currentPost = order;
        mShopOrderTextView.setText(currentPost.getDesc());
    }

    static ShopOrderHolder create(ViewGroup parent, CoffeeShop shop, UserAccount user) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoporder, parent, false);
        return new ShopOrderHolder(parent.getContext(), shop, user, view);
    }


}
