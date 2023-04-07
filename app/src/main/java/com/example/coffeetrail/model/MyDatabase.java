package com.example.coffeetrail.model;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserAccount.class, CoffeeShop.class, ShopOrder.class}, version = 12,
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
                sInstance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,
                                "my_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
            //                        ContentValues c = new ContentValues();
            //                        c.put("name", "test1");
            //                        c.put("url", "test1");
            //                        c.put("location", "test1");
            //                        c.put("latlng", "test1");
                                    CoffeeShop c = new CoffeeShop("Third Way Café",
                                            "https://www.thirdwaycafe.org/",
                                            "3058 W Broad St, Columbus, OH 43204",
                                            "39.95539870923077, -83.07998996917694");

                                    sInstance.getCoffeeShopDao().insert(c);
                                    //b.CoffeeShopDao().insert("coffeeshop", 0, c);
                                    //db.execSQL("INSERT INTO coffeeshop (name, url, location, latlng) VALUES (\"test2\",\"test2\",\"test2\",\"test2\")");
                                }
                            })
                        .build();
//                sInstance = Room.databaseBuilder(
//                        context.getApplicationContext(),
//                        MyDatabase.class, "my_database").fallbackToDestructiveMigration().build();
            }

        }
        return sInstance;



    }
}