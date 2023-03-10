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

    @Query("SELECT rowid, name, url, location FROM coffeeshop")
    public LiveData<List<CoffeeShop>> getAllCoffeeShops();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(CoffeeShop coffeeShop);

    @Query("SELECT rowid, name, url, location FROM coffeeshop WHERE rowid=:rowid ")
    public CoffeeShop getCoffeeShopById(int rowid);

    @Query("SELECT rowid, name, url, location FROM coffeeshop WHERE name=:name ")
    public CoffeeShop getCoffeeShopByName(String name);

    @Query("SELECT rowid, name FROM coffeeshop WHERE rowid=:rowid ")
    public CoffeeShop getCoffeeShopNameId(int rowid);
    @Update
    public void update(CoffeeShop coffeeShop);
    @Delete
    public void delete(CoffeeShop coffeeShop);

    @Query("DELETE FROM coffeeshop")
    public void nukeTable();
}
