package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.UserAccountViewModel;

import java.util.List;

public class ShopListFragment extends Fragment {
    private CoffeeShopViewModel mShopViewModel;
    private Button mVisitButton;
    private RecyclerView mShopRecyclerView;
    private List<CoffeeShop> mShopList;
    private ShopAdapter mShopAdapter;
    private LiveData<List<CoffeeShop>> mShopLiveData = new MutableLiveData<>();
    private FragmentShopListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mShopViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(CoffeeShopViewModel.class);
        fillCoffeeShopTable();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShopListBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void fillCoffeeShopTable(){
        CoffeeShop c1 = new CoffeeShop("The Bexley Coffee Shop", "https://www.facebook.com/BexleyCoffeeShop/", "492 N Cassady Ave Bexley, OH 43209" );
        mShopViewModel.insert(c1);

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        mShopRecyclerView = view.findViewById(R.id.shop_recycler_view);
        mShopRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        showShops();
    }

    private void showShops() {

        mShopLiveData = mShopViewModel.getAllCoffeeShops();
        mShopList = mShopLiveData.getValue();
        if (mShopList != null) {
            mShopAdapter = new ShopListFragment.ShopAdapter(mShopList);
            mShopRecyclerView.setAdapter(mShopAdapter);
            mShopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }
    private static class ShopHolder extends RecyclerView.ViewHolder {
        private final TextView mShopTextView;

        ShopHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_shop, parent, false));

            mShopTextView = itemView.findViewById(R.id.post_info);
        }

        void bind(CoffeeShop shop) {
            String shopName = shop.getName();
            mShopTextView.setText(shopName);
        }
    }
    private class ShopAdapter extends RecyclerView.Adapter<ShopListFragment.ShopHolder> {

        private final List<CoffeeShop> mShopList;

        ShopAdapter(List<CoffeeShop> shopList) {
            mShopList = shopList;
        }

        @NonNull
        @Override
        public ShopListFragment.ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            return new ShopListFragment.ShopHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ShopListFragment.ShopHolder holder, int position) {
            CoffeeShop shop = mShopList.get(position);
            holder.bind(shop);
        }

        @Override
        public int getItemCount() {
            return mShopList.size();
        }
    }
}
