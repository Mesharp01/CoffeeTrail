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
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
    public LatLng userLocation;
    private static final String TAG = "ShopList:";

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
        findLocation(view);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void fillCoffeeShopTable(){
        //mShopViewModel.nukeTable();
        CoffeeShop c = new CoffeeShop( "Test Coffee Shop",
                "blank",
                "blank" ,
                "40.001811906256656, -83.01259787019028");
        mShopViewModel.insert(c);
        c = new CoffeeShop( "The Bexley Coffee Shop",
                "https://www.facebook.com/BexleyCoffeeShop/",
                "492 N Cassady Ave, Bexley, OH 43209" ,
                "39.98072154248498, -82.93141499180881");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Boston Stoker Coffee Co.",
                "https://bostonstoker.com/",
                "1101 W 1st Ave, Grandview Heights, OH 43212",
                "39.98272937683729, -83.03271079910144");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Bottoms Up Coffee",
                "https://www.bottomsupcoffee.com/",
                "1069 West Broad St, Columbus, Ohio 43222",
                "39.958731939177035, -83.0285495361505");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Brioso Coffee",
                "https://briosocoffee.com/",
                "53 N High St, Columbus, OH 43215",
                "39.963657044011946, -83.00100083373579");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Chocolate Café",
                "https://www.chocolatecafecolumbus.com/",
                "1855 Northwest Blvd, Columbus, OH 43212",
                "39.99629569297916, -83.04757091971237");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Coffee Connections of Hilliard",
                "https://connections.coffee/",
                "4004 Main St, Hilliard, OH 43026",
                "40.03355137526235, -83.15957643111837");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Community Grounds Coffee & Meeting House",
                "https://mycommunitygrounds.com/",
                "1134 Parsons Ave, Columbus, OH 43206",
                "39.94064423126, -82.98183857911383");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Crimson Cup Coffee and Tea",
                "https://www.crimsoncup.com/",
                "4541 N High St, Columbus, OH 43214",
                "40.054816849556346, -83.0200731400133");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Cup O Joe Coffee House",
                "https://www.cupojoe.com/",
                "2990 N High St, Columbus, OH 43202",
                "40.023264223040144, -83.01295883974394");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Florin Coffee",
                "https://www.florincoffee.com/",
                "874 Oakland Park Ave, Columbus, OH 43224",
                "40.03285182966685, -82.99063515518463");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Fox in the Snow Cafe",
                "https://www.foxinthesnow.com/",
                "1031 N 4th St, Columbus, OH 43201",
                "39.98423227566821, -82.9990942723746");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Java Central Café and Roasters",
                "https://javacentral.coffee/?v=7516fd43adaa",
                "20 S State St B, Westerville, OH 43081",
                "40.12748436219508, -82.92270555513912");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Kittie’s Cafe",
                "https://kittiescakes.com/",
                "2424 E Main St, Bexley, OH 43209",
                "39.95761331905781, -82.93317258336911");
        mShopViewModel.insert(c);
        c = new CoffeeShop("One Line Coffee",
                "https://www.onelinecoffee.com/",
                "745 N High St, Columbus, OH 43215",
                "39.97880450571279, -82.99930826602618");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Pistacia Vera",
                "https://www.pistaciavera.com/",
                "541 S 3rd St, Columbus, OH 43215",
                "39.95173844996031, -82.99373963849749");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Roaming Goat Coffee",
                "https://www.roaminggoatcoffee.com/Default.asp",
                "849 N High St, Columbus, OH 43215",
                "39.97986201336324, -83.0034588134321");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Saucy Brew Works",
                "https://www.saucybrewworks.com/",
                "443 W 3rd Ave, Columbus, OH 43201",
                "39.98435657868971, -83.01639542142169");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Stauf's Coffee Roasters",
                "https://www.staufs.com/",
                "1277 Grandview Ave, Columbus, OH 43212",
                "39.98420414833093, -83.04368174529284");
        mShopViewModel.insert(c);
        c = new CoffeeShop("The Roosevelt Coffeehouse",
                "https://www.staufs.com/",
                "300 E Long St, Columbus, OH 43215",
                "39.9668094574469, -82.99133378993994");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Third Way Café",
                "https://www.thirdwaycafe.org/",
                "3058 W Broad St, Columbus, OH 43204",
                "39.95539870923077, -83.07998996917694");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Winans Chocolates + Coffees",
                "https://www.winanscandies.com/",
                "897 S 3rd St, Columbus, OH 43206",
                "39.944629903683364, -82.9934295373535");
        mShopViewModel.insert(c);
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
                                // Update the cached copy of the words in the adapter.
                                adapter.submitList(shops);
                            });
                            fillCoffeeShopTable();
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

