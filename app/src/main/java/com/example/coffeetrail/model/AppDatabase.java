package com.example.coffeetrail.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserAccount.class, Order.class}, version = 1,
        exportSchema = false)
public abstract class AppDatabase extends
        RoomDatabase {
    public abstract UserAccountDao getUserAccountDao();
    public abstract OrderDao getOrderDao();
    private static volatile AppDatabase sInstance;
    private static final int sNumberOfThreads = 2;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(sNumberOfThreads);
    static AppDatabase getDatabase(
            final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class, "app_database").build();
            }
        }
        return sInstance;
    }
}