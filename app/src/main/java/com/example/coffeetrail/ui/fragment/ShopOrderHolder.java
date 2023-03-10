package com.example.coffeetrail.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;

public class ShopOrderHolder extends RecyclerView.ViewHolder {
    private final TextView mShopOrderTextView;
    private Button mEditButton;
    private Button mDeleteButton;

    private ShopOrderHolder(View itemView) {
        super(itemView);
        mShopOrderTextView = itemView.findViewById(R.id.list_item_shoporder);
        mEditButton = itemView.findViewById(R.id.edit_post_button);
        mDeleteButton = itemView.findViewById(R.id.delete_post_button);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "edit clicked", Toast.LENGTH_SHORT).show();

            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "delete clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    void bind(String textPost) {
        mShopOrderTextView.setText(textPost);
    }

    static ShopOrderHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoporder, parent, false);
        return new ShopOrderHolder(view);
    }


}
