package com.example.coffeetrail.ui.fragment;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.UserAccount;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment
        implements OnMapReadyCallback, OnMyLocationButtonClickListener, OnMyLocationClickListener {
    private static final String TAG = "Maps:";
    private GoogleMap mMap;
    private UserAccount currentUser;
    private CoffeeShop currentShop;
    private Location mLocation;
    private LatLng shopLocation;

    private final ActivityResultLauncher<String> mActivityResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    findLocation();
                } else {
                    //TODO:
                }
            });
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


        }
    };

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setHasOptionsMenu(true);
        Log.d(TAG, "OnCreate Called");
        registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                findLocation();
            }
        });
        //getMapAsync(this);
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
        shopLocation = new LatLng(39.98061501446167, -82.93161217824766);

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

    private void findLocation() {
        final Activity activity = requireActivity();
        LocationRequest locationRequest = new LocationRequest.Builder(1000).build();
        FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(activity);
        boolean locationPermission = hasLocationPermission();
        if (locationPermission) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Task locationResult = locationProvider.getLastLocation();
            locationResult.addOnCompleteListener(activity, task -> {
                if(task.isSuccessful()){
                    Log.d("Location: ", locationResult.getResult().toString());
                }else{

                }
            });

        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.addCircle(new CircleOptions()
                .center(new LatLng(40, -83))
                .radius(10000)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Context context = requireContext();
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        if (hasLocationPermission()) {
            findLocation();
        }
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Context context = requireContext();
        Toast.makeText(context, "Current location:\n" + location, Toast.LENGTH_LONG).show();
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
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
}