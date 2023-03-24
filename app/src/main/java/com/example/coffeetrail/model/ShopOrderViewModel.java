package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.Closeable;
import java.util.List;
import java.util.Objects;
import android.app.Application;

public class ShopOrderViewModel extends AndroidViewModel {
    private final ShopOrderRepository mRepository;
    private LiveData<List<ShopOrder>> mAllShopOrders;

    public ShopOrderViewModel(Application application) {
        super(application);
        mRepository = new ShopOrderRepository(application);
        mAllShopOrders = mRepository.getAllShopOrders();
    }

    public boolean containsShopOrder(ShopOrder shopOrder) {
        boolean shopOrderInList = false;

        LiveData<ShopOrder> shopOrderLiveData = mRepository.findShopOrderByUidAndSid(shopOrder);
        ShopOrder theOrder = shopOrderLiveData.getValue();
        if (theOrder == null) {
            return false;
        } else if (Objects.requireNonNull(theOrder).getShopName() == (shopOrder.getShopName()) &&
                Objects.requireNonNull(theOrder).getUserName() == (shopOrder.getUserName())) {
            shopOrderInList = true;
        }

        return shopOrderInList;
    }

    public LiveData<List<ShopOrder>> getShopOrdersForUserAndShop(String userName, String shopName)
    {
        LiveData<List<ShopOrder>> shopOrderLiveData = mRepository.findShopOrderByUidAndSid(userName, shopName);
        return shopOrderLiveData; }

    public LiveData<List<ShopOrder>> getAllShopOrders() { return mRepository.getAllShopOrders(); }
    public void insert(ShopOrder shopOrder) {
        mRepository.insert(shopOrder);
        mAllShopOrders = mRepository.getAllShopOrders();
    }

    public void delete(ShopOrder shopOrder) {
        mRepository.delete(shopOrder);
        mAllShopOrders = mRepository.getAllShopOrders();
    }
    public void update(ShopOrder shopOrder){
        mRepository.update(shopOrder);
        mAllShopOrders = mRepository.getAllShopOrders();
    }


    public void updatePostDesc(String desc, int oid){
        mRepository.update(desc, oid);
        mAllShopOrders = mRepository.getAllShopOrders();
    }

}
