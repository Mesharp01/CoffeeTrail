package com.example.coffeetrail.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserAccountRepository {
    private UserAccountDao mUserAccountDao;
    private LiveData<List<UserAccount>> mAllUserAccounts;
    UserAccountRepository(Application application) {
        MyDatabase db =
                MyDatabase.getDatabase(application);
        mUserAccountDao = db.getUserAccountDao();
        mAllUserAccounts = mUserAccountDao.getAllUserAccounts();
    }
    // Room executes all queries on a separate thread.
// Observed LiveData notify observer upon data change.
    LiveData<List<UserAccount>> getAllUserAccounts() {
        return mAllUserAccounts; }
    LiveData<UserAccount> findUserAccountByName(
            UserAccount userAccount) {
        return mUserAccountDao.findByName(
                userAccount.getName(), userAccount.getPassword());
    }
    // You MUST call on non-UI thread or app throws
// exception. I pass a Runnable object to thedatabase.
    void insert(UserAccount userAccount) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mUserAccountDao.insert(userAccount));  }
    void delete(UserAccount... userAccount) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mUserAccountDao.delete(userAccount));  }
    void update(UserAccount... userAccount) {
        MyDatabase.databaseWriteExecutor.execute(() ->
                mUserAccountDao.updateUserAccount(userAccount));  }
    void updatePassword(String newPassword, String username){
        MyDatabase.databaseWriteExecutor.execute(() ->
                mUserAccountDao.update(newPassword, username));
    }
    void deleteUser(String username, String password){
        UserAccount user = mUserAccountDao.findByName(username, password).getValue();
        MyDatabase.databaseWriteExecutor.execute(() ->
                mUserAccountDao.deleteAccount(username, password));
    }
// lambda expression
}