package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.ShopOrder;

public class ShopListAdapter extends ListAdapter<CoffeeShop, ShopListHolder> {
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
