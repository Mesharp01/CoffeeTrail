package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Fts4  /* Supports full-text search */
@Entity(tableName = "useraccount")
public class UserAccount {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mUid;
    @NonNull
    @ColumnInfo(name = "name")
    public String mName;
    @NonNull
    @ColumnInfo(name = "password")
    public String mPassword;
    public UserAccount(@NonNull String name,
                       @NonNull String password) {
        mName = name;
        mPassword = password;
    }

    public UserAccount(int id, @NonNull String name,
                       @NonNull String password) {
        mUid = id;
        mName = name;
        mPassword = password;
    }

    public String getName() { return mName; }
    public String getPassword() { return mPassword; }
    /* equals(), hashCode(), and toString() methods */
    public void equals(){

    }
    @Override
    public int hashCode(){

        return 0;
    }
    @NonNull
    public String toString(){

        return "";
    }


}