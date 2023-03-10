package com.example.coffeetrail.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.FragmentCommunication;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopOrderActivity;

import java.util.List;

public class ShopListAdapter extends ListAdapter<CoffeeShop, ShopListHolder> {
    private Button mPostButton;
    private Button mPastOrdersButton;
    public ShopListAdapter(@NonNull DiffUtil.ItemCallback<CoffeeShop> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ShopListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShopListHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ShopListHolder holder, int position) {
        CoffeeShop current = getItem(position);
        holder.bind(current.getName());
    }

    static class ShopListDiff extends DiffUtil.ItemCallback<CoffeeShop> {

        @Override
        public boolean areItemsTheSame(@NonNull CoffeeShop oldShop, @NonNull CoffeeShop newShop) {
            return oldShop == newShop;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CoffeeShop oldShop, @NonNull CoffeeShop newShop) {
            return oldShop.getName().equals(newShop.getName());
        }
    }
}

