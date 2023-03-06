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
public interface OrderDao {
    @Query("SELECT description, date FROM order")
    public LiveData<List<Order>> getAllOrders();
    @Query("SELECT oid, description, date FROM order WHERE user_id LIKE :uid AND shop_id LIKE :sid LIMIT 1")
    public LiveData<Order> findByUserIdAndShopId(int uid,
                                            int sid);
    @Query("SELECT description, date FROM order WHERE shop_id LIKE :sid")
    public LiveData<Order> getAllByShopId(int sid);

    @Query("SELECT description, date FROM order WHERE user_id LIKE :uid")
    public LiveData<Order> getAllByUserId(int uid);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Order order);
    @Update
    public void updateOrder(Order order);
    @Delete
    public void delete(Order order);
}
