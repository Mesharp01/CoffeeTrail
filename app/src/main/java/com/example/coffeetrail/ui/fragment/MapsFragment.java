package com.example.coffeetrail.ui.fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.model.UserAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import android.opengl.EGLConfig;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;



public class MapsFragment extends Fragment
        implements OnMapReadyCallback {
    private static final String TAG = "Maps:";
    private GoogleMap mMap;
    private UserAccount currentUser;
    private CoffeeShop currentShop;
    private ShopOrder newOrder;
    private Location mLocation;
    private LatLng userLocation;
    private LatLng shopLocation;
    private static final int DEFAULT_ZOOM = 15;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MapsInitializer.initialize(getContext());
            Log.d(TAG, "OnMapReady Called");
            mMap = googleMap;
            mMap.setBuildingsEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.addCircle(new CircleOptions()
                    .center(new LatLng(40, -83))
                    .radius(10000)
                    .strokeColor(Color.RED));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 17));
            mMap.addMarker(new MarkerOptions().position(shopLocation).title(currentShop.mName));
            findLocation();
            updateLocationUI();
        }
    };

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
        Log.d(TAG, "OnCreate Called");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "OnCreateView Called");
        Bundle bundle = this.getArguments();
        if (bundle.getSerializable("user") != null) {
            currentUser = (UserAccount) bundle.getSerializable("user");
        }
        if (bundle.getSerializable("shop") != null) {
            currentShop = (CoffeeShop) bundle.getSerializable("shop");
        }
        if (bundle.getSerializable("order") != null) {
            newOrder = (ShopOrder) bundle.getSerializable("order");
        }
        String latlngString = currentShop.mLatlng;
        String[] coordniates = latlngString.split("[,]", 0);
        double lat = Double.parseDouble(coordniates[0]);
        double lng = Double.parseDouble(coordniates[1]);
        shopLocation = new LatLng(lat, lng);

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "OnViewCreated Called");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    @SuppressLint("MissingPermission")
    private void findLocation() {
        try {
            final Activity activity = requireActivity();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(4000);
            locationRequest.setFastestInterval(2000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(activity);
            boolean locationPermission = hasLocationPermission();
            if (locationPermission) {
                updateLocationUI();
                Task locationResult = locationProvider.getLastLocation();
                locationResult.addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        mLocation = (Location) task.getResult();
                        if (mLocation != null) {
                            userLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            checkUserAndShopLocation();
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(shopLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }

                });

            }
        } catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private void checkUserAndShopLocation(){
        FragmentActivity activity = requireActivity();
        double userLat = userLocation.latitude;
        double userLong = userLocation.longitude;
        double shopLat = shopLocation.latitude;
        double shopLong = shopLocation.longitude;
        LatLng center = new LatLng(((userLat+shopLat)/2),((userLat+shopLat)/2));
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(userLocation, 13));

        float[] distance = new float[1];

        Location.distanceBetween(userLat, userLong, shopLat, shopLong, distance);
        Log.d("Distance between places: ", String.valueOf(distance[0]));
        // distance[0] is now the distance between these lat/lons in meters
        if (distance[0] < 100.0) {
            Toast.makeText(activity.getApplicationContext(), "User location verified", Toast.LENGTH_SHORT).show();
            ShopOrderViewModel mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
            mShopOrderViewModel.insert(newOrder);
            Toast.makeText(activity.getApplicationContext(), "New post added", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> returnToShopList(), 10000);
        } else{
            Toast.makeText(activity.getApplicationContext(), "Please get closer to the coffee shop", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> returnToPost(), 5000);
        }


    }
    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (hasLocationPermission()) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }
        }

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean lacksLocationPermission(){
        final Activity activity = requireActivity();
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result != PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasLocationPermission(){
        return !lacksLocationPermission();
    }

    private void returnToPost(){
        FragmentActivity activity = requireActivity();
        activity.getSupportFragmentManager().popBackStack();
    }
    private void returnToShopList(){
        ShopListFragment listFragment = new ShopListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", currentUser);
        bundle.putSerializable("shop", currentShop);
        listFragment.setArguments(bundle);
        FragmentManager fm = getParentFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .addToBackStack(null)
                .commit();
    }

}