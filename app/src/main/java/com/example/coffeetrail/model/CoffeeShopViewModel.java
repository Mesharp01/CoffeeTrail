package com.example.coffeetrail.model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.io.Closeable;
import java.util.List;

public class CoffeeShopViewModel extends AndroidViewModel {
    private CoffeeShopRepository mRepository;
    private final LiveData<List<CoffeeShop>> mAllCoffeeShops;
    public CoffeeShopViewModel(Application application) {
        super(application);
        mRepository = new CoffeeShopRepository(application);
        mAllCoffeeShops = mRepository.getAllCoffeeShops();
    }
    public LiveData<List<CoffeeShop>> getAllCoffeeShops() { return mAllCoffeeShops; }
    public void insert(CoffeeShop coffeeShop) { mRepository.insert(coffeeShop); }


}
