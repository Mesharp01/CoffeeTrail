package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.FragmentCommunication;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.model.UserAccountViewModel;
import com.example.coffeetrail.ui.activity.MakePostActivity;
import com.example.coffeetrail.ui.activity.ShopListActivity;

public class ShopListFragment extends Fragment{
    private CoffeeShopViewModel mShopViewModel;
    private FragmentShopListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoplist_recycler_view, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ShopListAdapter adapter = new ShopListAdapter(new ShopListAdapter.ShopListDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);

        mShopViewModel.getAllCoffeeShops().observe(this, shops -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shops);
        });
        fillCoffeeShopTable();

    }

    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void fillCoffeeShopTable(){
        mShopViewModel.nukeTable();
        CoffeeShop c1 = new CoffeeShop(1, "The Bexley Coffee Shop", "https://www.facebook.com/BexleyCoffeeShop/", "492 N Cassady Ave Bexley, OH 43209" );
        CoffeeShop c2 = new CoffeeShop(2,"Boston Stoker Coffee Co.", "https://bostonstoker.com/", "10855 Engle Rd Vandalia, OH 45377");
        mShopViewModel.insert(c1);
        mShopViewModel.insert(c2);
    }
}

