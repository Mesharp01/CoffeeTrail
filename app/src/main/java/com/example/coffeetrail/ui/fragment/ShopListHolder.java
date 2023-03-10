package com.example.coffeetrail.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.ui.fragment.ShopListFragment;
import com.example.coffeetrail.R;
import com.example.coffeetrail.model.FragmentCommunication;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopListActivity;
import com.example.coffeetrail.ui.activity.ShopOrderActivity;

public class ShopListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView mShopListTextView;
    private Button mVisitButton;
    private Button mViewOrdersButton;

    private FragmentCommunication mCommunicator;
    private Context mContext;


    public ShopListHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mShopListTextView = itemView.findViewById(R.id.list_item_shoplist);
        mViewOrdersButton = itemView.findViewById(R.id.see_orders_button);
        mVisitButton = itemView.findViewById(R.id.visit_shop_button);
        mVisitButton.setOnClickListener(this);
        mViewOrdersButton.setOnClickListener(this);
    }

    void bind(String text) {
        mShopListTextView.setText(text);
    }

    static ShopListHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoplist, parent, false);
        return new ShopListHolder(parent.getContext(), view);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        ShopListFragment shopListFragment = (ShopListFragment) ((AppCompatActivity)mContext).getSupportFragmentManager().findFragmentByTag("SHOP_LIST_FRAGMENT");
        //CoffeeShop shop = shopListFragment.getCoffeeShopByName(mShopListTextView.getText().toString());
        //Log.d("coffee shop", shop.toString());

        String storeName = mShopListTextView.getText().toString();
        if (viewId == R.id.visit_shop_button) {
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Bundle bundle = shopListFragment.getArguments();
            Bundle bundleShopList = new Bundle();
            bundleShopList.putString("name", storeName);
            //bundleShopList.putInt("sid", shop.getShopId());
            bundle.putBundle("bundleShopList", bundleShopList);

            MakePostFragment postFragment = new MakePostFragment();
            postFragment.setArguments(bundle);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postFragment).addToBackStack(null).commit();
        } else if (viewId == R.id.see_orders_button) {

            Bundle bundle = shopListFragment.getArguments();
            Bundle bundleShopList = new Bundle();
            bundleShopList.putString("name", storeName);
            //bundleShopList.putInt("sid", shop.getShopId());
            bundle.putBundle("bundleShopList", bundleShopList);

            ShopOrderFragment orderFragment = new ShopOrderFragment();
            orderFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderFragment).addToBackStack(null).commit();
        }
    }
}
