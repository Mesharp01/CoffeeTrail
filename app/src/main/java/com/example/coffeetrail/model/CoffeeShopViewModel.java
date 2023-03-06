package com.example.coffeetrail.model;

        import android.app.Application;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.ViewModel;

        import java.io.Closeable;
        import java.util.List;

public class CoffeeShopViewModel extends ViewModel {
    private CoffeeShopRepository mRepository;
    private final LiveData<List<CoffeeShop>> mAllCoffeeShops;
    public CoffeeShopViewModel(@NonNull Application application) {
        super((Closeable) application);
        mRepository = new CoffeeShopRepository(application);
        mAllCoffeeShops = mRepository.getAllCoffeeShops();
    }
    // Methods for fetching a UserAccount, checking if UserAccount exists
    public LiveData<List<CoffeeShop>> getAllCoffeeShops() { return mAllCoffeeShops; }
    public void insert(CoffeeShop coffeeShop) { mRepository.insert(coffeeShop); }
}
