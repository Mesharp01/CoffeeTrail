package com.example.coffeetrail.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoffeeShopRepository {
    private CoffeeShopDao mCoffeeShopDao;
    private LiveData<List<CoffeeShopViewModel>> mAllCoffeeShops;

    CoffeeShopRepository(Application application) {
        Database db =
                AppDatabase.getDatabase(application);
        mCoffeeShopDao = db.getCoffeeShopDao();
        mAllCoffeeShops = mCoffeeShopDao.getAllCoffeeShops();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<CoffeeShopViewModel>> getAllCoffeeShops() {
        return mAllCoffeeShops; }

    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(CoffeeShopViewModel coffeeShop) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.insert(coffeeShop)); }
// lambda expression
}
