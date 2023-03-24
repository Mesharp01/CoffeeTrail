package com.example.coffeetrail.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

public class UserAccountViewModel extends AndroidViewModel {
    private UserAccountRepository mRepository;
    private LiveData<List<UserAccount>> mAllUserAccounts;
    public UserAccountViewModel(Application application) {
        super(application);
        mRepository = new UserAccountRepository(application);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }
    // Methods for fetching a UserAccount, checking if UserAccount exists
    public LiveData<List<UserAccount>> getAllUserAccounts() { return mAllUserAccounts; }
    public void insert(UserAccount userAccount) {
        mRepository.insert(userAccount);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }

    public void delete(UserAccount userAccount){
        mRepository.delete(userAccount);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }
    public void update(UserAccount userAccount){
        mRepository.update(userAccount);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }
    public void updatePassword(String newPassword, String username){
        mRepository.updatePassword(newPassword, username);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }
    public void deleteUser(String username, String password){
        mRepository.deleteUser(username, password);
        mAllUserAccounts = mRepository.getAllUserAccounts();
    }

}