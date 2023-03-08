package com.example.coffeetrail.model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.io.Closeable;
import java.util.List;

public class CoffeeShopViewModel extends ViewModel implements View.OnClickListener {
    private CoffeeShopRepository mRepository;
    private final LiveData<List<CoffeeShop>> mAllCoffeeShops;
    public CoffeeShopViewModel(@NonNull Application application) {
        super((Closeable) application);
        mRepository = new CoffeeShopRepository(application);
        mAllCoffeeShops = mRepository.getAllCoffeeShops();
    }
    public LiveData<List<CoffeeShop>> getAllCoffeeShops() { return mAllCoffeeShops; }
    public void insert(CoffeeShop coffeeShop) { mRepository.insert(coffeeShop); }

    @Override
    public void onClick(View view) {

    }
}
