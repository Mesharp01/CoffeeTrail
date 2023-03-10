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
        mShopOrderDao = db.getShopOrderDao();
        mAllOrders = mShopOrderDao.getAllShopOrders();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<ShopOrder>> getAllShopOrders() {
        return mAllOrders; }

    LiveData<List<ShopOrder>> findShopOrderByUidAndSid(String uid, String sid) {
        return mShopOrderDao.findAllByUserAndShop(uid, sid); }
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

    void delete(ShopOrder o) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.delete(o));  }

    void update(ShopOrder o) {
        AppDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.updateOrder(o));  }

    public void update(String description, int oid){
        AppDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.update(description, oid));  }

}
