package com.example.coffeetrail.model;

import android.app.Application;
import java.util.List;
import androidx.lifecycle.LiveData;

public class ShopOrderRepository {
    private ShopOrderDao mShopOrderDao;
    private LiveData<List<ShopOrder>> mAllOrders;
    ShopOrderRepository(Application application) {
        AppDatabase db =
                AppDatabase.getDatabase(application);
        mShopOrderDao = db.getOrderDao();
        mAllOrders = mShopOrderDao.getAllShopOrders();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<ShopOrder>> getAllShopOrders() {
        return mAllOrders; }
    LiveData<ShopOrder> findShopOrderByUidAndSid(
            ShopOrder o) {
        return mShopOrderDao.findByUserIdAndShopId(
                o.getUserId(), o.getShopId());
    }
    LiveData<ShopOrder> findShopOrderByUid(
            ShopOrder o) {
        return mShopOrderDao.getAllByUserId(o.getUserId());
    }
    LiveData<ShopOrder> findShopOrderBySid(
            ShopOrder o) {
        return mShopOrderDao.getAllByShopId(o.getShopId());
    }
    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(ShopOrder o) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.insert(o));  }
// lambda expression
}
