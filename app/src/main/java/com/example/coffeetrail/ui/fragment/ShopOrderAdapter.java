package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.ShopOrder;

public class ShopOrderAdapter extends ListAdapter<ShopOrder, ShopOrderHolder> {
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
        holder.bind(current);
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
