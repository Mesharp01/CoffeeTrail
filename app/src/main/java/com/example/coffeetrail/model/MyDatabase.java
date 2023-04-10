package com.example.coffeetrail.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserAccount.class, CoffeeShop.class, ShopOrder.class}, version = 9,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MyDatabase extends
        RoomDatabase {
    public abstract UserAccountDao getUserAccountDao();
    public abstract CoffeeShopDao getCoffeeShopDao();
    public abstract ShopOrderDao getShopOrderDao();
    private static volatile MyDatabase sInstance;
    private static final int sNumberOfThreads = 2;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(sNumberOfThreads);
    static MyDatabase getDatabase(
            final Context context) {
        if (sInstance == null) {
            synchronized (MyDatabase.class) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        MyDatabase.class, "my_database").fallbackToDestructiveMigration().build();
            }
        }
        return sInstance;
    }
}