package com.example.coffeetrail.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import java.util.List;

public class CoffeeShopRepository {
    private CoffeeShopDao mCoffeeShopDao;
    private LiveData<List<CoffeeShop>> mAllCoffeeShops;

    CoffeeShopRepository(Application application) {
        AppDatabase db =
                AppDatabase.getDatabase(application);
        mCoffeeShopDao = db.getCoffeeShopDao();
        mAllCoffeeShops = mCoffeeShopDao.getAllCoffeeShops();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<CoffeeShop>> getAllCoffeeShops() {
        return mAllCoffeeShops; }

    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(CoffeeShop coffeeShop) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.insert(coffeeShop)); }
// lambda expression
CoffeeShop getCoffeeShopById(int id) {
    final CoffeeShop[] shop = new CoffeeShop[1];
    AppDatabase.databaseWriteExecutor.execute( () ->
            shop[0] = mCoffeeShopDao.getCoffeeShopById(id));
    return shop[0];

}

    void nukeTable(){
        AppDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.nukeTable()); }

}
