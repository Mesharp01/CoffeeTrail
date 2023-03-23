package com.example.coffeetrail.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ShopOrderDao {
    @Query("SELECT oid, user_name, shop_name, description, date FROM shoporder")
    public LiveData<List<ShopOrder>> getAllShopOrders();

    @Query("SELECT oid, description, date FROM shoporder WHERE user_name LIKE :username AND shop_name LIKE :shopname")
    public LiveData<List<ShopOrder>> findAllByUserAndShop(String username, String shopname);
    @Query("SELECT oid, user_name, shop_name, description, date FROM shoporder WHERE user_name LIKE :username AND shop_name LIKE :shopname LIMIT 1")
    public LiveData<ShopOrder> findByUserIdAndShopId(String username, String shopname);
    @Query("SELECT oid, user_name, shop_name, description, date FROM shoporder WHERE shop_name LIKE :shopname")
    public LiveData<ShopOrder> getAllByShopId(String shopname);

    @Query("SELECT oid, user_name, shop_name, description, date FROM shoporder WHERE user_name LIKE :username")
    public LiveData<ShopOrder> getAllByUserId(String username);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ShopOrder shopOrder);
    @Update
    public void updateOrder(ShopOrder shopOrder);
    @Delete
    public void delete(ShopOrder shopOrder);
    @Query("UPDATE shoporder SET description=:description WHERE oid = :oid")
    public void update(String description, int oid);
}
