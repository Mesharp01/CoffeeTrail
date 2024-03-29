package com.example.coffeetrail.ui.fragment;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeetrail.R;
import com.example.coffeetrail.model.CoffeeShop;
import com.example.coffeetrail.model.CoffeeShopViewModel;
import com.example.coffeetrail.model.ShopOrder;
import com.example.coffeetrail.model.ShopOrderViewModel;
import com.example.coffeetrail.model.UserAccount;

public class MakePostFragment extends Fragment implements View.OnClickListener{
    private Button mPostButton;
    private TextView mPostContent;
    private String currentPost;

    private EditText mPost;
    private CoffeeShopViewModel mShopViewModel;
    private TextView mTitleTextView;

    private UserAccount currentUser;
    private CoffeeShop currentStore;
    private MapsFragment mapsFragment;
    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_post, container, false);

        Bundle bundle = this.getArguments();
        if(bundle.getSerializable("user") != null){
            currentUser = (UserAccount) bundle.getSerializable("user");
        }
        if(bundle.getSerializable("shop") != null){
            currentStore = (CoffeeShop) bundle.getSerializable("shop");
        }
        mPostButton = v.findViewById(R.id.post_button);
        mPostContent = v.findViewById(R.id.post_text);
        //mPostContent.setText(mShopViewModel.getStoreName(currentStore));
        mPost = v.findViewById(R.id.post_edit);
        mPostButton.setOnClickListener(this);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("user", currentUser);
        bundle2.putSerializable("shop", currentStore);
        mapsFragment = new MapsFragment();
        mapsFragment.setArguments(bundle2);
        fm = getParentFragmentManager();
        fm.beginTransaction()
                .add(R.id.fragment_container, mapsFragment)
                .hide(mapsFragment)
                .addToBackStack("maps_fragment")
                .commit();

        return v;
    }
    public ShopOrder createShopOrder(){
        FragmentActivity activity = requireActivity();
        final String post = mPost.getText().toString();
        //need to access current shop and user to make a new shopOrder entry in the database...
        ShopOrder shopOrder = new ShopOrder(post, currentUser.getName(), currentStore.getName());
        return shopOrder;
    }

    public boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.post_button) {
            FragmentActivity activity = requireActivity();
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            // hide the keyboard
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            ShopOrder newOrder = createShopOrder();
            String postContent = newOrder.getDesc();

            Bundle bundle = new Bundle();
            bundle.putSerializable("order", newOrder);

            if(isNetworkAvailable() && isGPSEnabled(this.getContext())){
                mapsFragment.setArguments(bundle);
                mapsFragment.runMap();
                fm.beginTransaction().hide(this).show(mapsFragment).commit();
            }
            else if(!isGPSEnabled(this.getContext())){
                Toast.makeText(activity.getApplicationContext(), "Connect to GPS Signal to Post!", Toast.LENGTH_SHORT).show();
                activity.getSupportFragmentManager().popBackStack();
            }
            else if(!isNetworkAvailable()){
                Toast.makeText(activity.getApplicationContext(), "Connect to the internet to post!", Toast.LENGTH_SHORT).show();
                activity.getSupportFragmentManager().popBackStack();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();    }
}