package com.example.coffeetrail.ui.fragment;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccount;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class ShopListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView mShopListTextView;
    private Button mAboutButton;
    private Button mVisitButton;
    private Button mViewOrdersButton;
    private TextView mShopDistance;

    //private String currentStore;
    private UserAccount currentUser;
    private CoffeeShop currentStore;
    private static LatLng mUserLocation;
    private static LatLng mShopLocation;
    private String distanceBetween;
//    private ShopOrder mNewPost;

    private CoffeeShopViewModel mViewModel;



    public ShopListHolder(View itemView, UserAccount user){
        super(itemView);
        mShopListTextView = itemView.findViewById(R.id.list_item_shoplist);
        mViewOrdersButton = itemView.findViewById(R.id.see_orders_button);
        mAboutButton = itemView.findViewById(R.id.about_button);
        mVisitButton = itemView.findViewById(R.id.visit_shop_button);
        mShopDistance = itemView.findViewById(R.id.distance);
        //mAboutButton.setOnClickListener(this);
        mVisitButton.setOnClickListener(this);
        mViewOrdersButton.setOnClickListener(this);
        currentUser = user;

    }

    void bind(CoffeeShop shop) {
        currentStore = shop;
        mShopListTextView.setText(shop.getName());
        checkUserAndShopLocation();
    }

    static ShopListHolder create(ViewGroup parent, UserAccount user, LatLng userLocation){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_shoplist, parent, false);
        mUserLocation = userLocation;
        return new ShopListHolder(view, user);
    }

    @Override
    public void onClick(View view) {
        final int viewId = view.getId();
        if(viewId == R.id.about_button){
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(currentStore.getLocation() + "\n" + currentStore.getUrl());
            builder.setTitle(currentStore.getName());
            builder.setCancelable(false);
            builder.setNegativeButton("Close", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if (viewId == R.id.visit_shop_button) {
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
    }private void checkUserAndShopLocation() {
        String latlngString = currentStore.mLatlng;
        String[] coordniates = latlngString.split("[,]", 0);
        double lat = Double.parseDouble(coordniates[0]);
        double lng = Double.parseDouble(coordniates[1]);
        mShopLocation = new LatLng(lat, lng);
        double userLat = mUserLocation.latitude;
        double userLong = mUserLocation.longitude;
        double shopLat = lat;
        double shopLong = lng;



        float[] distance = new float[1];
        Location.distanceBetween(userLat, userLong, shopLat, shopLong, distance);
        double distanceMiles = distance[0]/1609.334;
        distanceBetween = String.format("%.2fmi", distanceMiles);
        Log.d("Distance between places: ", distanceBetween);
        mShopDistance.setText(distanceBetween);
    }
}
