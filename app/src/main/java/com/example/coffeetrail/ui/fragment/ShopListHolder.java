package com.example.coffeetrail.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;

public class ShopListHolder extends RecyclerView.ViewHolder {
    private final TextView mShopListTextView;

    private ShopListHolder(View itemView) {
        super(itemView);
        mShopListTextView = itemView.findViewById(R.id.list_item_shoplist);
    }

    void bind(String text) {
        mShopListTextView.setText(text);
    }

    static ShopListHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoplist, parent, false);
        return new ShopListHolder(view);
    }
}
