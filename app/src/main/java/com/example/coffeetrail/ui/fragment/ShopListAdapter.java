package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;

public class ShopListAdapter extends ListAdapter<CoffeeShop, ShopListHolder> {
    private Button mPostButton;
    private Button mPastOrdersButton;
    private String currentStore;
    private String currentUser;

    private String currentPost;

    private CoffeeShopViewModel mViewModel;
    public ShopListAdapter(@NonNull DiffUtil.ItemCallback<CoffeeShop> diffCallback, String user, String store, String post) {
        super(diffCallback);
        currentStore = store;
        currentUser = user;
        currentPost = post;
    }

    @Override
    public ShopListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShopListHolder.create(parent, currentStore, currentUser, currentPost);
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

