package com.example.coffeetrail.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeetrail.R;
import com.example.coffeetrail.databinding.FragmentShopListBinding;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;

public class ShopListFragment extends Fragment{
    private CoffeeShopViewModel mShopViewModel;
    private FragmentShopListBinding binding;
    private TextView mNameTextView;
    public String currentUser;
    public String currentUsername;
    public String currentStore;
    public String currentPost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shoplist_recycler_view, container, false);
        Bundle bundle = this.getArguments();

        if(bundle.getString("shop") != null){
            currentStore = bundle.get("shop").toString();

        }
        if(bundle.getString("userId") != null){
            currentUser = bundle.get("userId").toString();
        }
        if(bundle.getString("userName") != null){
            currentUsername = bundle.get("userName").toString();
            mNameTextView = v.findViewById(R.id.name_text_view);
            mNameTextView.setText("Hello " + currentUsername);
        }
        if(bundle.get("postContent") != null){
            currentPost = bundle.get("postContent").toString();
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        mShopViewModel = new ViewModelProvider(this).get(CoffeeShopViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        final ShopListAdapter adapter = new ShopListAdapter(new ShopListAdapter.ShopListDiff(), currentUser, currentStore, currentPost);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        mShopViewModel.getAllCoffeeShops().observe(this, shops -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(shops);
        });
        fillCoffeeShopTable();

    }

    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void fillCoffeeShopTable(){
        //mShopViewModel.nukeTable();
        CoffeeShop c = new CoffeeShop( "The Bexley Coffee Shop", "https://www.facebook.com/BexleyCoffeeShop/", "492 N Cassady Ave Bexley, OH 43209" );
        mShopViewModel.insert(c);
        c = new CoffeeShop("Boston Stoker Coffee Co.", "https://bostonstoker.com/", "10855 Engle Rd Vandalia, OH 45377");
        mShopViewModel.insert(c);
    }
}

