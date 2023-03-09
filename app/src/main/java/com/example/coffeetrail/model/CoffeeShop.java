package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Fts4 /* Supports full-text search */
@Entity(tableName = "coffeeshop")
public class CoffeeShop {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mSid;
    @NonNull
    @ColumnInfo(name = "name")
    public String mName;
    @NonNull
    @ColumnInfo(name = "url")
    public String mUrl;

    @NonNull
    @ColumnInfo(name = "location")
    public String mLocation;

    public CoffeeShop(@NonNull String name,
                               @NonNull String url, @NonNull String location) {
        mName = name;
        mUrl = url;
        mLocation = location;
    }
    public CoffeeShop(int id, @NonNull String name,
                      @NonNull String url, @NonNull String location) {
        mSid = id;
        mName = name;
        mUrl = url;
        mLocation = location;
    }
    public String getName() { return mName; }
    public String getUrl() { return mUrl; }
    public String getLocation() { return mLocation; }


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


