package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.FragmentCommunication;
import com.example.coffeetrail.model.ShopOrder;

public class ShopOrderAdapter extends ListAdapter<ShopOrder, ShopOrderHolder> {
    private String currentStore;
    private String currentUser;

    private String currentPost;
    public ShopOrderAdapter(@NonNull DiffUtil.ItemCallback<ShopOrder> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ShopOrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ShopOrderHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ShopOrderHolder holder, int position) {
        ShopOrder current = getItem(position);
        holder.bind(current.getDesc());
    }

    static class ShopOrderDiff extends DiffUtil.ItemCallback<ShopOrder> {

        @Override
        public boolean areItemsTheSame(@NonNull ShopOrder oldItem, @NonNull ShopOrder newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShopOrder oldItem, @NonNull ShopOrder newItem) {
            return oldItem.getDesc().equals(newItem.getDesc());
        }
    }
}
