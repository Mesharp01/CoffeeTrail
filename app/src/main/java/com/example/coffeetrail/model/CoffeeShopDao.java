package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoffeeShopDao {

    @Query("SELECT rowid, name, url, location, latlng, distance FROM coffeeshop ORDER BY distance ASC")
    public LiveData<List<CoffeeShop>> getAllCoffeeShops();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(CoffeeShop coffeeShop);

    @Query("SELECT rowid, name, url, location, latlng, distance  FROM coffeeshop WHERE rowid=:rowid ")
    public CoffeeShop getCoffeeShopById(int rowid);

    @Query("SELECT rowid, name, url, location, latlng, distance  FROM coffeeshop WHERE name=:name ")
    public CoffeeShop getCoffeeShopByName(String name);

    @Query("SELECT rowid, name, distance FROM coffeeshop WHERE rowid=:rowid ")
    public CoffeeShop getCoffeeShopNameId(int rowid);

    @Update
    public void update(CoffeeShop coffeeShop);

    @Query("UPDATE coffeeshop SET distance=:distance WHERE rowid = :rowid")
    public void update(double distance, int rowid);

    @Delete
    public void delete(CoffeeShop coffeeShop);

    @Query("DELETE FROM coffeeshop")
    public void nukeTable();
}
