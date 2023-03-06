package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.io.Closeable;
import java.util.List;
import java.util.Objects;
import android.app.Application;

public class ShopOrderViewModel extends ViewModel {
    private final ShopOrderRepository mRepository;
    private LiveData<List<ShopOrder>> mAllShopOrders;

    public ShopOrderViewModel(@NonNull Application application) {
        super((Closeable) application);
        mRepository = new ShopOrderRepository(application);
        mAllShopOrders = mRepository.getAllShopOrders();
    }

    public boolean containsShopOrder(ShopOrder shopOrder) {
        boolean shopOrderInList = false;

        LiveData<ShopOrder> shopOrderLiveData = mRepository.findShopOrderByUidAndSid(shopOrder);
        ShopOrder theOrder = shopOrderLiveData.getValue();
        if (theOrder == null) {
            return false;
        } else if (Objects.requireNonNull(theOrder).getShopId() == (shopOrder.getShopId()) &&
                Objects.requireNonNull(theOrder).getUserId() == (shopOrder.getUserId())) {
            shopOrderInList = true;
        }

        return shopOrderInList;
    }

    public LiveData<ShopOrder> getShopOrder(ShopOrder shopOrder) {
        return mRepository.findShopOrderByUidAndSid(shopOrder);
    }

    public LiveData<List<ShopOrder>> getAllShopOrders() { return mRepository.getAllShopOrders(); }

    public void insert(ShopOrder shopOrder) {
        mRepository.insert(shopOrder);
        mAllShopOrders = mRepository.getAllShopOrders();
    }
}