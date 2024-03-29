package com.example.coffeetrail.model;

import android.app.Application;
import java.util.List;
import androidx.lifecycle.LiveData;

public class ShopOrderRepository {
    private ShopOrderDao mShopOrderDao;
    private LiveData<List<ShopOrder>> mAllOrders;
    ShopOrderRepository(Application application) {
        MyDatabase db =
                MyDatabase.getDatabase(application);
        mShopOrderDao = db.getShopOrderDao();
        mAllOrders = mShopOrderDao.getAllShopOrders();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<ShopOrder>> getAllShopOrders() {
        return mAllOrders; }

    LiveData<List<ShopOrder>> findShopOrderByUidAndSid(String uid, String sid) {
        return mShopOrderDao.findAllByUserAndShop(uid, sid); }

    List<ShopOrder> findShopOrderByUid(String uid) {
        return mShopOrderDao.findAllByUser(uid);
    }

    LiveData<ShopOrder> findShopOrderByUidAndSid(
            ShopOrder o) {
        return mShopOrderDao.findByUserIdAndShopId(
                o.getUserName(), o.getShopName());
    }
    LiveData<ShopOrder> findShopOrderBySid(
            ShopOrder o) {
        return mShopOrderDao.getAllByShopId(o.getShopName());
    }
    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(ShopOrder o) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.insert(o));  }

    void delete(ShopOrder o) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.delete(o));  }

    void update(ShopOrder o) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.updateOrder(o));  }

    public void update(String description, int oid){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mShopOrderDao.update(description, oid));  }

}
