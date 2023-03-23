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
    @Query("SELECT oid, user_id, shop_id, description, date FROM shoporder")
    public LiveData<List<ShopOrder>> getAllShopOrders();

    @Query("SELECT oid, user_id, shop_id, description, date FROM shoporder WHERE user_id LIKE :uid AND shop_id LIKE :sid")
    public LiveData<List<ShopOrder>> findAllByUserAndShop(String uid, String sid);
    @Query("SELECT oid, user_id, shop_id, description, date FROM shoporder WHERE user_id LIKE :uid AND shop_id LIKE :sid LIMIT 1")
    public LiveData<ShopOrder> findByUserIdAndShopId(String uid, String sid);
    @Query("SELECT oid, user_id, shop_id, description, date FROM shoporder WHERE shop_id LIKE :sid")
    public LiveData<ShopOrder> getAllByShopId(String sid);

    @Query("SELECT oid, user_id, shop_id, description, date FROM shoporder WHERE user_id LIKE :uid")
    public LiveData<ShopOrder> getAllByUserId(String uid);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(ShopOrder shopOrder);
    @Update
    public void updateOrder(ShopOrder shopOrder);
    @Delete
    public void delete(ShopOrder shopOrder);

    @Query("UPDATE shoporder SET description=:description WHERE oid = :oid")
    public void update(String description, int oid);
    @Query("DELETE FROM shoporder")
    public void nukeTable();
}
