package com.example.coffeetrail.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopListActivity;
import com.example.coffeetrail.ui.activity.ShopOrderActivity;

public class ShopListHolder extends RecyclerView.ViewHolder {
    private final TextView mShopListTextView;
    private Button mVisitButton;
    private Button mViewOrdersButton;
    private Context context;
    private Intent intent;


    private ShopListHolder(View itemView) {
        super(itemView);
        mShopListTextView = itemView.findViewById(R.id.list_item_shoplist);
        mViewOrdersButton = itemView.findViewById(R.id.see_orders_button);
        mVisitButton = itemView.findViewById(R.id.visit_shop_button);
    }

    void bind(String text) {
        mShopListTextView.setText(text);
    }

    static ShopListHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoplist, parent, false);
        return new ShopListHolder(view);
    }

    //@Override
    /*public void onClick(View view) {
        final int viewId = view.getId();
        if (viewId == R.id.see_orders_button) {
            context = view.getContext();
            intent =  new Intent(context, ShopOrderActivity.class);
        } else if (viewId == R.id.visit_shop_button) {
            context = view.getContext();
            intent =  new Intent(context, MakePostActivity.class);
        }
        view.getContext().startActivity(intent);
    }*/
}
