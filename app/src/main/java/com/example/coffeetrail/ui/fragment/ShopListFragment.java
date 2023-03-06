package com.example.coffeetrail.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShopViewModel;

public class ShopListFragment extends Fragment {
    private CoffeeShopViewModel mShop;
    private Button mVisitButton;
    private FragmentShopListBinding binding;
    String[] shopArray = {"Starbucks","Dunkin","Kafe Kerouac","Sweetwaters",
            "WebOS","Ubuntu","Windows7","Max OS X"};

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
}
