package com.example.coffeetrail.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserAccountDao {
    @Query("SELECT rowid, name, password  FROM useraccount")
    public LiveData<List<UserAccount>> getAllUserAccounts();
    @Query("SELECT rowid, name, password FROM useraccount WHERE name LIKE :name AND password LIKE :password")
    public LiveData<UserAccount> findByName(String name,
            String password);

    @Query("UPDATE useraccount SET password=:newPassword WHERE name = :username")
    public void update(String newPassword, String username);

    @Query("DELETE FROM useraccount WHERE name LIKE :username AND password LIKE :password AND rowid LIKE :rowid")
    public void deleteAccount(String username, String password, int rowid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(UserAccount userAccount);
    @Update
    public void updateUserAccount(UserAccount... userAccount);
    @Delete
    public void delete(UserAccount... user);
}