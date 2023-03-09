package com.example.coffeetrail.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
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

        mPostButton = holder.itemView.findViewById(R.id.visit_shop_button);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MakePostActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        mPastOrdersButton = holder.itemView.findViewById(R.id.see_orders_button);
        mPastOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ShopOrderActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MakePostActivity.class);
                view.getContext().startActivity(intent);
            }
        });*/
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
