package com.example.coffeetrail.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

//@Fts4
@Entity(tableName = "shoporder", foreignKeys = {
                @ForeignKey(entity = UserAccount.class,
                        parentColumns = "name",
                        childColumns = "user_name"),
                @ForeignKey(entity = CoffeeShop.class,
                        parentColumns = "name",
                        childColumns = "shop_name")
        })
public class ShopOrder implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int oid;
    @ColumnInfo(name = "description")
    public String desc;
    @ColumnInfo(name = "date")
    public Date date;
    @ColumnInfo(name = "user_name")
    public String mUsername;
    @ColumnInfo(name = "shop_name")
    public String mShopname;

    public ShopOrder(@NonNull String postContent, @NonNull String username, @NonNull String shopname){
        desc = postContent;
        mUsername = username;
        mShopname = shopname;
        date = new Date();
    }

    public ShopOrder(){
    }
    public String getShopName(){return mShopname;}

    public String getUserName(){return mUsername;}

    public int getOrderId(){return oid;}

    public String getDesc(){return desc;}

    public void setDesc(){

    }

}