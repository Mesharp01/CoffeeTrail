package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

//@Fts4 /* Supports full-text search */
@Entity(tableName = "coffeeshop", indices = {@Index(value = {"name"},
        unique = true)})
public class CoffeeShop implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int mSid;

    //@NonNull
    @ColumnInfo(name = "name")
    public String mName;
    //@NonNull
    @ColumnInfo(name = "url")
    public String mUrl;

    //@NonNull
    @ColumnInfo(name = "location")
    public String mLocation;

    @ColumnInfo(name = "latlng")
    public String mLatlng;

    @ColumnInfo(name = "distance")
    public float mDistance;

    public CoffeeShop(@NonNull String name,
                               @NonNull String url, @NonNull String location,
                      @NonNull String latlng) {
        mName = name;
        mUrl = url;
        mLocation = location;
        mLatlng = latlng;
        mDistance = 0;
    }
    public int getId() { return mSid; }
    public String getName() { return mName; }
    public String getUrl() { return mUrl; }
    public String getLocation() { return mLocation; }
    public String getLatlng() { return mLatlng; }
    public double getDistance(){ return mDistance;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o.equals(this);
    }
    @Override
    public int hashCode() {
        return Objects.hash(mSid, mName, mUrl, mLocation);
    }
    @NonNull
    @Override
    public String toString() {
        return "CoffeeShop{" +
                "sid=" + mSid +
                "; name='" + mName + '\'' +
                "; url='" + mUrl + '\'' +
                "; location='" + mLocation + '\'' +
                '}';
    }
}


