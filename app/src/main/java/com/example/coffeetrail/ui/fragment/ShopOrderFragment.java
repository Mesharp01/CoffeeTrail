package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;

import java.util.List;

public class ShopOrderFragment extends Fragment {
    public ShopOrderViewModel mShopOrderViewModel;
    private List<ShopOrder> mShopOrderList;
    private ShopOrderAdapter mShopOrderAdapter;
    private String storeName;
    private String postContent;
    private int shopId;
    private int userId;

    private LiveData<List<ShopOrder>> mShopOrderLiveData = new MutableLiveData<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
        mShopOrderViewModel.getAllShopOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoporder_recycler_view, container, false);
        Bundle bundle = this.getArguments();
        Bundle bundleShopList = bundle.getBundle("bundleShopList");
        Bundle bundleShopOrder = bundle.getBundle("bundleShopOrder");
        Bundle bundleAccount = bundle.getBundle("bundleAccount");
        if(bundle != null) {
            storeName = bundleShopList.getString("name");
            shopId = bundleShopList.getInt("sid");
            postContent = bundleShopOrder.getString("postContent");
            userId = bundleShopOrder.getInt("uid");
            ShopOrder o1 = new ShopOrder(postContent, userId, shopId);
            mShopOrderViewModel.insert(o1);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ShopOrderAdapter adapter = new ShopOrderAdapter(new ShopOrderAdapter.ShopOrderDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //fillShopOrderTable();
    }

    private void fillShopOrderTable(){
        ShopOrder o1 = new ShopOrder(storeName, 1, 1);

        mShopOrderViewModel.insert(o1);

    }
    public int getItemCount() {
        return mShopOrderList.size();
    }
}