package com.example.coffeetrail.model;

import android.app.Application;
import java.util.List;
import androidx.lifecycle.LiveData;

public class OrderRepository {
    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllOrders;
    OrderRepository(Application application) {
        AppDatabase db =
                AppDatabase.getDatabase(application);
        mOrderDao = db.getOrderDao();
        mAllOrders = mOrderDao.getAllOrders();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<Order>> getOrders() {
        return mAllOrders; }
    LiveData<Order> findOrderByUidAndSid(
            Order o) {
        return mOrderDao.findByUserIdAndShopId(
                o.getUserId(), o.getShopId());
    }
    LiveData<Order> findOrderByUid(
            Order o) {
        return mOrderDao.getAllByUserId(o.getUserId());
    }
    LiveData<Order> findOrderBySid(
            Order o) {
        return mOrderDao.getAllByShopId(o.getShopId());
    }
    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(Order o) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mOrderDao.insert(o));  }
// lambda expression
}
