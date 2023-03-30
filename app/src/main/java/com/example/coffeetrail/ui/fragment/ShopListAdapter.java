package com.example.coffeetrail.ui.fragment;

import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccount;
import com.google.android.gms.maps.model.LatLng;

public class ShopListAdapter extends ListAdapter<CoffeeShop, ShopListHolder> {
    private Button mPostButton;
    private Button mPastOrdersButton;
    private CoffeeShop currentStore;
    private UserAccount currentUser;
    private LatLng mUserLocation;
    private ShopOrder mNewPost;

    private CoffeeShopViewModel mViewModel;
    public ShopListAdapter(@NonNull DiffUtil.ItemCallback<CoffeeShop> diffCallback, UserAccount user, LatLng userLocation){//, CoffeeShop store, ShopOrder newPost) {
        super(diffCallback);
        currentUser = user;
        mUserLocation = userLocation;
    }

    @Override
    public ShopListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return ShopListHolder.create(parent, currentUser, mUserLocation);//, currentStore);//currentStore, currentUser, mNewPost);
    }

    @Override
    public void onBindViewHolder(ShopListHolder holder, int position) {
        currentStore = getItem(position);
        holder.bind(currentStore);
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

