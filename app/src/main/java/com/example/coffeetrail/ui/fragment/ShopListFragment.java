package com.example.coffeetrail.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.model.UserAccount;
import com.example.coffeetrail.ui.activity.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShopListFragment extends Fragment{
    private CoffeeShopViewModel mShopViewModel;
    private ShopOrderViewModel mShopOrderViewModel;
    private FragmentShopListBinding binding;
    private TextView mNameTextView;
    private ProgressBar pgsBar;
    public UserAccount currentUser;
    public CoffeeShop currentStore;
    public ShopOrder newPost;
    private View gView;
    public LatLng userLocation;
    private static final String TAG = "ShopList:";


    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    findLocation(gView);
                } else {
                    if (lacksLocationPermission()) {
                        Toast.makeText(requireActivity(), "Please allow location to use the app", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(() -> requestLocation(), 5000);
                    }
                }
            });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activity activity = requireActivity();
        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoplist_recycler_view, container, false);
        Bundle bundle = this.getArguments();
        pgsBar = v.findViewById(R.id.trail_progress);

        if(bundle.getSerializable("user") != null){
            currentUser = (UserAccount) bundle.getSerializable("user");
            mNameTextView = v.findViewById(R.id.name_text_view);
            mNameTextView.setText("Hello " + currentUser.getName());
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        gView = view;
        requestLocation();
    }
    private void requestLocation(){
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    public void setProgress(String user){
        new Thread(new Runnable() {
            public void run() {
                List<ShopOrder> orders = mShopOrderViewModel.getShopOrdersForUser(user);
                //Set<ShopOrder> uniqueOrders = new HashSet<>(orders);
                List<ShopOrder> uniqueOrders = new ArrayList<>();
                for(ShopOrder o:orders){
                    if (!uniqueOrders.contains(o)){
                        uniqueOrders.add(o);
                    }
                }
                int progress = uniqueOrders.size();
                pgsBar.setProgress(progress);
            }
        }).start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_shop_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Activity activity = requireActivity();
        switch (item.getItemId()) {
            case R.id.help_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.app_how_to_desc);
                builder.setTitle(R.string.app_how_to);
                builder.setCancelable(false);
                builder.setNegativeButton("Get Started!", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            case R.id.change_font_button:
                TypedValue outValue = new TypedValue();
                getContext().getTheme().resolveAttribute(R.attr.themeName, outValue, true);
                if("dyslexiaFont".equals(outValue.string)){
                    getContext().getTheme().applyStyle(R.style.RegFontTheme, true);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    bundle.putSerializable("shop", currentStore);

                    ShopListFragment listFragment = new ShopListFragment();
                    listFragment.setArguments(bundle);

                    AppCompatActivity startActivity = (AppCompatActivity) getContext();
                    startActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).addToBackStack(null).commit();
                }
                else{
                    getContext().getTheme().applyStyle(R.style.DyslexiaTheme, true);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", currentUser);
                    bundle.putSerializable("shop", currentStore);

                    ShopListFragment listFragment = new ShopListFragment();
                    listFragment.setArguments(bundle);

                    AppCompatActivity startActivity = (AppCompatActivity) getContext();
                    startActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listFragment).addToBackStack(null).commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("MissingPermission")
    private void findLocation(@NonNull View view) {
        try {
            final Activity activity = requireActivity();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(4000);
            locationRequest.setFastestInterval(2000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            FusedLocationProviderClient locationProvider = LocationServices.getFusedLocationProviderClient(activity);
            boolean locationPermission = hasLocationPermission();
            if (locationPermission) {
                Task locationResult = locationProvider.getLastLocation();
                locationResult.addOnCompleteListener(activity, task -> {
                    Location mLocation;
                    if (task.isSuccessful()) {
                        mLocation = (Location) task.getResult();
                        if (mLocation != null) {
                            userLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                            Log.d(TAG, userLocation.toString());
                            mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);
                            mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
                            mShopOrderViewModel.getAllShopOrders();

                            RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
                            final ShopListAdapter adapter = new ShopListAdapter(new ShopListAdapter.ShopListDiff(), currentUser, userLocation);//, currentStore, newPost);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                            mShopViewModel.getAllCoffeeShops().observe(this, shops -> {
                                List<CoffeeShop> shopsWithDistances = calculateDistances(shops);
                                Collections.sort(shopsWithDistances, new Comparator<CoffeeShop>() {
                                    @Override
                                    public int compare(CoffeeShop lhs, CoffeeShop rhs) {
                                        return lhs.getDistance() < rhs.getDistance() ? -1 : (lhs.getDistance() > rhs.getDistance() ) ? 1 : 0;
                                    }
                                });
                                // Update the cached copy of the words in the adapter.
                                adapter.submitList(shopsWithDistances);
                            });
                            //fillCoffeeShopTable();
                            setProgress(currentUser.getName());
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                    }

                });

            }
        } catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private List<CoffeeShop> calculateDistances(List<CoffeeShop> shops){
        List<CoffeeShop> newShops = new ArrayList<CoffeeShop>();
        for (CoffeeShop s:shops) {
            newShops.add(checkUserAndShopLocation(s));
        }

        return newShops;
    }

    private CoffeeShop checkUserAndShopLocation(CoffeeShop s) {
        LatLng mUserLocation = userLocation;
        String latlngString = s.mLatlng;
        String[] coordniates = latlngString.split("[,]", 0);
        double lat = Double.parseDouble(coordniates[0]);
        double lng = Double.parseDouble(coordniates[1]);
        LatLng mShopLocation = new LatLng(lat, lng);
        double userLat = mUserLocation.latitude;
        double userLong = mUserLocation.longitude;
        double shopLat = mShopLocation.latitude;
        double shopLong = mShopLocation.longitude;

        float[] distance = new float[1];
        Location.distanceBetween(userLat, userLong, shopLat, shopLong, distance);
        double distanceMiles = distance[0]/1609.334;
        s.setDistance(distanceMiles);
        return s;
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
}

