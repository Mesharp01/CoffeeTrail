package com.example.coffeetrail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class ShopListFragment extends Fragment {
    private Shop mShop;
    private Button mVisitButton;
    String[] shopArray = {"Starbucks","Dunkin","Kafe Kerouac","Sweetwaters",
            "WebOS","Ubuntu","Windows7","Max OS X"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shop_list, container, false);


//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_shop_list, shopArray);

//        ListView listView = (ListView) v.findViewById(R.id.shops_list);
//        listView.setAdapter(adapter);

        mShop = new Shop();
        mShop.setName("Coffee Shop");

        mVisitButton = (Button) v.findViewById(R.id.visit_button);
        mVisitButton.setEnabled(true);



        return v;
    }
}
