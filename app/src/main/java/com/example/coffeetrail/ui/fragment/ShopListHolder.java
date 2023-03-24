package com.example.coffeetrail.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccount;

public class ShopListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView mShopListTextView;
    private Button mVisitButton;
    private Button mViewOrdersButton;
    private Button mAboutButton;

    //private String currentStore;
    private UserAccount currentUser;
    private CoffeeShop currentStore;
//    private ShopOrder mNewPost;

    private CoffeeShopViewModel mViewModel;



    public ShopListHolder(View itemView, UserAccount user){
        super(itemView);
        mShopListTextView = itemView.findViewById(R.id.list_item_shoplist);
        mAboutButton = itemView.findViewById(R.id.about_button);
        mViewOrdersButton = itemView.findViewById(R.id.see_orders_button);
        mVisitButton = itemView.findViewById(R.id.visit_shop_button);
        mAboutButton.setOnClickListener(this);
        mVisitButton.setOnClickListener(this);
        mViewOrdersButton.setOnClickListener(this);
        currentUser = user;
    }

    void bind(CoffeeShop shop) {
        currentStore = shop;
        mShopListTextView.setText(shop.getName());
    }

    static ShopListHolder create(ViewGroup parent, UserAccount user){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoplist, parent, false);
        return new ShopListHolder(view, user);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if(viewId == R.id.about_button){
            AboutPopUpHelper popUpClass = new AboutPopUpHelper();
            popUpClass.showPopupWindow(view, currentStore);
        } else if (viewId == R.id.visit_shop_button) {
            String storeName = mShopListTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            bundle.putSerializable("shop", currentStore);

            MakePostFragment postFragment = new MakePostFragment();
            postFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, postFragment).addToBackStack(null).commit();
        } else if (viewId == R.id.see_orders_button) {
            String storeName = mShopListTextView.getText().toString();

            Bundle bundle = new Bundle();
            bundle.putSerializable("user", currentUser);
            bundle.putSerializable("shop", currentStore);

            ShopOrderFragment orderFragment = new ShopOrderFragment();
            orderFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, orderFragment, "SHOP_ORDER_FRAGMENT").addToBackStack(null).commit();
        }
    }
}
