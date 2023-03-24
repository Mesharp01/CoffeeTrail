package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccount;

public class ShopOrderAdapter extends ListAdapter<ShopOrder, ShopOrderHolder> {
    private CoffeeShop currentStore;
    private UserAccount currentUser;
    private ShopOrder currentPost;
    //private ShopOrder currentPost;
    public ShopOrderAdapter(@NonNull DiffUtil.ItemCallback<ShopOrder> diffCallback,UserAccount user, CoffeeShop store) {
        super(diffCallback);
        currentStore = store;
        currentUser = user;
    }

    @Override
    public ShopOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShopOrderHolder.create(parent, currentStore, currentUser);
    }

    @Override
    public void onBindViewHolder(ShopOrderHolder holder, int position) {
        currentPost = getItem(position);
        holder.bind(currentPost);
    }

    static class ShopOrderDiff extends DiffUtil.ItemCallback<ShopOrder> {

        @Override
        public boolean areItemsTheSame(@NonNull ShopOrder oldItem, @NonNull ShopOrder newItem) {
            return oldItem.getOrderId() == newItem.getOrderId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShopOrder oldItem, @NonNull ShopOrder newItem) {
            return oldItem.getDesc().equals(newItem.getDesc());
        }
    }
}
