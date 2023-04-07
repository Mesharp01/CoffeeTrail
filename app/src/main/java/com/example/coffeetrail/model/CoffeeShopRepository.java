package com.example.coffeetrail.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CoffeeShopRepository {
    private CoffeeShopDao mCoffeeShopDao;
    private LiveData<List<CoffeeShop>> mAllCoffeeShops;

    CoffeeShopRepository(Application application) {
        MyDatabase db =
                MyDatabase.getDatabase(application);
        mCoffeeShopDao = db.getCoffeeShopDao();
        mAllCoffeeShops = mCoffeeShopDao.getAllCoffeeShops();
    }

    public String findShopById(int id) {
        CoffeeShop shop = getCoffeeShopById(id);
        String name = shop.getName();
        return name;
    }

    public int findShopByName(String name) {
        CoffeeShop shop = getCoffeeShopByName(name);
        int id = shop.getId();
        return id;
    }

    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<CoffeeShop>> getAllCoffeeShops() {
        return mAllCoffeeShops; }

    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(CoffeeShop coffeeShop) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.insert(coffeeShop)); }
//    }
// lambda expression
    CoffeeShop getCoffeeShopById(int id) {
        final CoffeeShop[] shop = new CoffeeShop[1];
        MyDatabase.databaseWriteExecutor.execute( () ->
                shop[0] = mCoffeeShopDao.getCoffeeShopById(id));
        return shop[0];

    }

    CoffeeShop getCoffeeShopByName(String name) {
        final CoffeeShop[] shop = new CoffeeShop[1];
        MyDatabase.databaseWriteExecutor.execute( () ->
                shop[0] = mCoffeeShopDao.getCoffeeShopByName(name));
        return shop[0];

    }

    void update(double distance, int sid){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.update(distance, sid));
    }

    void nukeTable(){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.nukeTable()); }

}
