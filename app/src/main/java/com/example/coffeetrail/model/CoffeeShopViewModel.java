package com.example.coffeetrail.model;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.io.Closeable;
import java.util.List;

public class CoffeeShopViewModel extends AndroidViewModel {
    private CoffeeShopRepository mRepository;
    private final LiveData<List<CoffeeShop>> mAllCoffeeShops;
    private boolean dataInitialized = false;
    public CoffeeShopViewModel(Application application) {
        super(application);
        mRepository = new CoffeeShopRepository(application);
        mAllCoffeeShops = mRepository.getAllCoffeeShops();
        if(dataInitialized){
            //fillCoffeeShopTable();
        }
    }
    public LiveData<List<CoffeeShop>> getAllCoffeeShops() { return mRepository.getAllCoffeeShops(); }
    public void insert(CoffeeShop coffeeShop) {
        int id = coffeeShop.getId();
        if (mRepository.getCoffeeShopById(id) == null) {
            mRepository.insert(coffeeShop);
        }
    }

    public String getStoreName(int shopId){
        String name = mRepository.findShopById(shopId);
        return name;
    }

    public int getStoreId(String name){
        int id = mRepository.findShopByName(name);
        return id;
    }

    public void update(double distance, int sid){
        mRepository.update(distance, sid);
    }

    public void nukeTable(){
        mRepository.nukeTable();
    }

    private void fillCoffeeShopTable(){
        //mShopViewModel.nukeTable();
        CoffeeShop c = new CoffeeShop("Test Coffee Shop",
                "blank",
                "blank",
                "39.990552178060554, -83.00813045127312");
        insert(c);
        c = new CoffeeShop("The Bexley Coffee Shop",
                "https://www.facebook.com/BexleyCoffeeShop/",
                "492 N Cassady Ave, Bexley, OH 43209",
                "39.98072154248498, -82.93141499180881");
        insert(c);
        c = new CoffeeShop("Boston Stoker Coffee Co.",
                "https://bostonstoker.com/",
                "1101 W 1st Ave, Grandview Heights, OH 43212",
                "39.98272937683729, -83.03271079910144");
        insert(c);
        c = new CoffeeShop("Bottoms Up Coffee",
                "https://www.bottomsupcoffee.com/",
                "1069 West Broad St, Columbus, Ohio 43222",
                "39.958731939177035, -83.0285495361505");
        insert(c);
        c = new CoffeeShop("Brioso Coffee",
                "https://briosocoffee.com/",
                "53 N High St, Columbus, OH 43215",
                "39.963657044011946, -83.00100083373579");
        insert(c);
        c = new CoffeeShop("Chocolate Café",
                "https://www.chocolatecafecolumbus.com/",
                "1855 Northwest Blvd, Columbus, OH 43212",
                "39.99629569297916, -83.04757091971237");
        insert(c);
        c = new CoffeeShop("Coffee Connections of Hilliard",
                "https://connections.coffee/",
                "4004 Main St, Hilliard, OH 43026",
                "40.03355137526235, -83.15957643111837");
        insert(c);
        c = new CoffeeShop("Community Grounds Coffee & Meeting House",
                "https://mycommunitygrounds.com/",
                "1134 Parsons Ave, Columbus, OH 43206",
                "39.94064423126, -82.98183857911383");
        insert(c);
        c = new CoffeeShop("Crimson Cup Coffee and Tea",
                "https://www.crimsoncup.com/",
                "4541 N High St, Columbus, OH 43214",
                "40.054816849556346, -83.0200731400133");
        insert(c);
        c = new CoffeeShop("Cup O Joe Coffee House",
                "https://www.cupojoe.com/",
                "2990 N High St, Columbus, OH 43202",
                "40.023264223040144, -83.01295883974394");
        insert(c);
        c = new CoffeeShop("Florin Coffee",
                "https://www.florincoffee.com/",
                "874 Oakland Park Ave, Columbus, OH 43224",
                "40.03285182966685, -82.99063515518463");
        insert(c);
        c = new CoffeeShop("Fox in the Snow Cafe",
                "https://www.foxinthesnow.com/",
                "1031 N 4th St, Columbus, OH 43201",
                "39.98423227566821, -82.9990942723746");
        insert(c);
        c = new CoffeeShop("Java Central Café and Roasters",
                "https://javacentral.coffee/?v=7516fd43adaa",
                "20 S State St B, Westerville, OH 43081",
                "40.12748436219508, -82.92270555513912");
        insert(c);
        c = new CoffeeShop("Kittie’s Cafe",
                "https://kittiescakes.com/",
                "2424 E Main St, Bexley, OH 43209",
                "39.95761331905781, -82.93317258336911");
        insert(c);
        c = new CoffeeShop("One Line Coffee",
                "https://www.onelinecoffee.com/",
                "745 N High St, Columbus, OH 43215",
                "39.97880450571279, -82.99930826602618");
        insert(c);
        c = new CoffeeShop("Pistacia Vera",
                "https://www.pistaciavera.com/",
                "541 S 3rd St, Columbus, OH 43215",
                "39.95173844996031, -82.99373963849749");
        insert(c);
        c = new CoffeeShop("Roaming Goat Coffee",
                "https://www.roaminggoatcoffee.com/Default.asp",
                "849 N High St, Columbus, OH 43215",
                "39.97986201336324, -83.0034588134321");
        insert(c);
        c = new CoffeeShop("Saucy Brew Works",
                "https://www.saucybrewworks.com/",
                "443 W 3rd Ave, Columbus, OH 43201",
                "39.98435657868971, -83.01639542142169");
        insert(c);
        c = new CoffeeShop("Stauf's Coffee Roasters",
                "https://www.staufs.com/",
                "1277 Grandview Ave, Columbus, OH 43212",
                "39.98420414833093, -83.04368174529284");
        insert(c);
        c = new CoffeeShop("The Roosevelt Coffeehouse",
                "https://www.staufs.com/",
                "300 E Long St, Columbus, OH 43215",
                "39.9668094574469, -82.99133378993994");
        insert(c);
        c = new CoffeeShop("Third Way Café",
                "https://www.thirdwaycafe.org/",
                "3058 W Broad St, Columbus, OH 43204",
                "39.95539870923077, -83.07998996917694");
        insert(c);
        c = new CoffeeShop("Winans Chocolates + Coffees",
                "https://www.winanscandies.com/",
                "897 S 3rd St, Columbus, OH 43206",
                "39.944629903683364, -82.9934295373535");
        insert(c);
        dataInitialized = true;

    }

}
