package com.example.coffeetrail.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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


    public ShopListHolder(View itemView) {
        super(itemView);
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
        return new ShopListHolder(view);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if (viewId == R.id.visit_shop_button) {
            String storeName = mShopListTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("name", storeName);

            MakePostFragment postFragment = new MakePostFragment();
            postFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postFragment).addToBackStack(null).commit();
        } else if (viewId == R.id.see_orders_button) {
            String storeName = mShopListTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("name", storeName);

            ShopOrderFragment orderFragment = new ShopOrderFragment();
            orderFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderFragment).addToBackStack(null).commit();
        }
    }
}
