package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.UserAccountViewModel;

public class ShopListFragment extends Fragment {
    private CoffeeShopViewModel mShopViewModel;
    private Button mVisitButton;
    private FragmentShopListBinding binding;
    String[] shopArray = {"Starbucks","Dunkin","Kafe Kerouac","Sweetwaters",
            "WebOS","Ubuntu","Windows7","Max OS X"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mShopViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(CoffeeShopViewModel.class);
        fillCoffeeShopTable();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopListBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void fillCoffeeShopTable(){
        CoffeeShop c1 = new CoffeeShop("The Bexley Coffee Shop", "https://www.facebook.com/BexleyCoffeeShop/", "492 N Cassady Ave Bexley, OH 43209" );

        mShopViewModel.insert(c1);

    }
}
