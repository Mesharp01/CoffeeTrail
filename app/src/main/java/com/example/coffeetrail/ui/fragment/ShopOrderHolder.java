package com.example.coffeetrail.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;

public class ShopOrderHolder extends RecyclerView.ViewHolder {
    private final TextView mShopOrderTextView;

    private ShopOrderHolder(View itemView) {
        super(itemView);
        mShopOrderTextView = itemView.findViewById(R.id.list_item_shoporder);
    }

    void bind(String text) {
        mShopOrderTextView.setText(text);
    }

    static ShopOrderHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoporder, parent, false);
        return new ShopOrderHolder(view);
    }
}
