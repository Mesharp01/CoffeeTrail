package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
        mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);
        mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
        mShopOrderViewModel.getAllShopOrders();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ShopListAdapter adapter = new ShopListAdapter(new ShopListAdapter.ShopListDiff(), currentUser);//, currentStore, newPost);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        mShopViewModel.getAllCoffeeShops().observe(this, shops -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shops);
        });
        fillCoffeeShopTable();
        setProgress(currentUser.getName());

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
        CoffeeShop c = new CoffeeShop( "The Bexley Coffee Shop",
                "https://www.facebook.com/BexleyCoffeeShop/",
                "39.98061501446167, -82.93161217824766" );
        mShopViewModel.insert(c);
        c = new CoffeeShop("Boston Stoker Coffee Co.",
                "https://bostonstoker.com/",
                "10855 Engle Rd, Vandalia, OH 45377");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Bottoms Up Coffee",
                "https://www.bottomsupcoffee.com/",
                "11069 West Broad St, Columbus, Ohio 43222");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Brioso Coffee",
                "https://briosocoffee.com/",
                "53 N High St, Columbus, OH 43215");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Chocolate Café",
                "https://www.chocolatecafecolumbus.com/",
                "1855 Northwest Blvd, Columbus, OH 43212");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Coffee Connections of Hilliard",
                "https://connections.coffee/",
                "4004 Main St, Hilliard, OH 43026");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Community Grounds Coffee & Meeting House",
                "https://mycommunitygrounds.com/",
                "1134 Parsons Ave, Columbus, OH 43206");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Crimson Cup Coffee and Tea",
                "https://www.crimsoncup.com/",
                "4541 N High St, Columbus, OH 43214");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Cup O Joe Coffee House",
                "https://www.cupojoe.com/",
                "2990 N High St, Columbus, OH 43202");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Florin Coffee",
                "https://www.florincoffee.com/",
                "874 Oakland Park Ave, Columbus, OH 43224");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Fox in the Snow Cafe",
                "https://www.foxinthesnow.com/",
                "1031 N 4th St, Columbus, OH 43201");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Java Central Café and Roasters",
                "https://javacentral.coffee/?v=7516fd43adaa",
                "20 S State St B, Westerville, OH 43081");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Kittie’s Cafe",
                "https://kittiescakes.com/",
                "2424 E Main St, Bexley, OH 43209");
        mShopViewModel.insert(c);
        c = new CoffeeShop("One Line Coffee",
                "https://www.onelinecoffee.com/",
                "745 N High St, Columbus, OH 43215");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Pistacia Vera",
                "https://www.pistaciavera.com/",
                "541 S 3rd St, Columbus, OH 43215");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Roaming Goat Coffee",
                "https://www.roaminggoatcoffee.com/Default.asp",
                "849 N High St, Columbus, OH 43215");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Saucy Brew Works",
                "https://www.saucybrewworks.com/",
                "443 W 3rd Ave, Columbus, OH 43201");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Stauf's Coffee Roasters",
                "https://www.staufs.com/",
                "1277 Grandview Ave, Columbus, OH 43212");
        mShopViewModel.insert(c);
        c = new CoffeeShop("The Roosevelt Coffeehouse",
                "https://www.staufs.com/",
                "300 E Long St, Columbus, OH 43215");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Third Way Café",
                "https://www.thirdwaycafe.org/",
                "3058 W Broad St, Columbus, OH 43204");
        mShopViewModel.insert(c);
        c = new CoffeeShop("Winans Chocolates + Coffees",
                "https://www.winanscandies.com/",
                "897 S 3rd St, Columbus, OH 43206");
        mShopViewModel.insert(c);
    }


}

