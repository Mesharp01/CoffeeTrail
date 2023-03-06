package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;

import java.util.List;

public class ShopOrderFragment extends Fragment {
    private ShopOrderViewModel mShopOrderViewModel;
    private RecyclerView mShopOrderRecyclerView;
    private List<ShopOrder> mShopOrderList;
    private ShopOrderAdapter mShopOrderAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mShopOrderViewModel = new ShopOrderViewModel(activity.getApplication());
        mShopOrderViewModel.getAllShopOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoporder, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        mShopOrderRecyclerView = view.findViewById(R.id.shop_order_recycler_view);
        mShopOrderRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void showShopOrders() {

        ShopOrderLiveData allShopOrdersData = mShopOrderViewModel.getAllShopOrders();
        mShopOrderList = allShopOrdersData.getValue();
        if (mShopOrderList != null) {
            mShopOrderAdapter = new ShopOrderAdapter(mShopOrderList);
            mShopOrderRecyclerView.setAdapter(mShopOrderAdapter);
            mShopOrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
    private static class ShopOrderHolder extends RecyclerView.ViewHolder {
        private final TextView mShopOrderTextView;

        ShopOrderHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_shop_order, parent, false));

            mShopOrderTextView = itemView.findViewById(R.id.post_info);
        }

        void bind(ShopOrder shopOrder) {
            String post = shopOrder.getDesc();
            mShopOrderTextView.setText(post);
        }
    }
    private class ShopOrderAdapter extends RecyclerView.Adapter<ShopOrderHolder> {

        private final List<ShopOrder> mShopOrderList;

        ShopOrderAdapter(List<ShopOrder> shopOrderList) {
            mShopOrderList = shopOrderList;
        }

        @NonNull
        @Override
        public ShopOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            return new ShopOrderHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopOrderHolder holder, int position) {
            ShopOrder shopOrder = mShopOrderList.get(position);
            holder.bind(shopOrder);
        }

        @Override
        public int getItemCount() {
            return mShopOrderList.size();
        }
    }
}