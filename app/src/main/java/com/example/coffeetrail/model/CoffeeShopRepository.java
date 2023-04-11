package com.example.coffeetrail.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CoffeeShopRepository {
    private CoffeeShopDao mCoffeeShopDao;
    private LiveData<List<CoffeeShop>> mAllCoffeeShops;

    CoffeeShopRepository(Application application) {
        MyDatabase db =
                MyDatabase.getDatabase(application);
        mCoffeeShopDao = db.getCoffeeShopDao();
        mAllCoffeeShops = mCoffeeShopDao.getAllCoffeeShops();
    }



    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<CoffeeShop>> getAllCoffeeShops() {
        return mCoffeeShopDao.getAllCoffeeShops();}

    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(CoffeeShop coffeeShop) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.insert(coffeeShop)); }


    List<CoffeeShop> getCoffeeShopsByName(String name) {
        final List<CoffeeShop> shops = mCoffeeShopDao.getCoffeeShopsByName(name);
        Log.d("fillCoffeeShopTable","repo name"+name);
        return shops;

    }


    void update(double distance, int sid){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.update(distance, sid));
    }

    void nukeTable(){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mCoffeeShopDao.nukeTable()); }

}
