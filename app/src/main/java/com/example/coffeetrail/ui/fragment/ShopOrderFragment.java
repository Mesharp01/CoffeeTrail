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
import com.example.coffeetrail.model.UserAccount;

import java.util.List;

public class ShopOrderFragment extends Fragment {
    public ShopOrderViewModel mShopOrderViewModel;
    private List<ShopOrder> mShopOrderList;
    private ShopOrderAdapter mShopOrderAdapter;
    private TextView mShopTextView;
    private CoffeeShop currentStore;
    private UserAccount currentUser;
    private ShopOrder currentPost;

    private LiveData<List<ShopOrder>> mShopOrderLiveData = new MutableLiveData<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mShopOrderViewModel = new ViewModelProvider(this).get(ShopOrderViewModel.class);
        //mShopOrderViewModel.getAllShopOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoporder_recycler_view, container, false);
        Bundle bundle = this.getArguments();
        if(bundle != null) {
            if(bundle.getSerializable("user") != null){
                currentUser = (UserAccount) bundle.getSerializable("user");
            }
            if(bundle.getSerializable("shop") != null){
                currentStore = (CoffeeShop) bundle.getSerializable("shop");
                mShopTextView = v.findViewById(R.id.shop_name_text_view);
                mShopTextView.setText("Posts for " + currentStore.getName());
            }
//            if(bundle.getSerializable("shoporder") != null){
//                currentPost = (ShopOrder) bundle.getSerializable("shoporder");
//                ShopOrder o1 = new ShopOrder(currentPost.getDesc(), currentUser.getName(), currentStore.getName());
//                mShopOrderViewModel.insert(o1);
//            }
//            if(bundle.getString("shop") != null){
//                storeName = bundle.get("shop").toString();
//                mShopTextView = v.findViewById(R.id.shop_name_text_view);
//                mShopTextView.setText("Posts for " + storeName);
//            }
//            if(bundle.getString("userId") != null){
//                userId = bundle.get("userId").toString();
//            }
//            if(bundle.get("postContent") != null){
//                postContent = bundle.get("postContent").toString();
//                ShopOrder o1 = new ShopOrder(postContent, userId, storeName);
//                mShopOrderViewModel.insert(o1);
//            }
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ShopOrderAdapter adapter = new ShopOrderAdapter(new ShopOrderAdapter.ShopOrderDiff(), currentUser, currentStore);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        mShopOrderViewModel.getShopOrdersForUserAndShop(currentUser.getName(), currentStore.getName()).observe(this, orders -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(orders);
        });
    }
}