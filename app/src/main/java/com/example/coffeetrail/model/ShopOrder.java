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
                        childColumns = "user_id"),
                @ForeignKey(entity = CoffeeShop.class,
                        parentColumns = "name",
                        childColumns = "shop_id")
        })
public class ShopOrder implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int oid;

    @ColumnInfo(name = "description")
    public String desc;


    @ColumnInfo(name = "date")
    public Date date;


    @ColumnInfo(name = "user_id")
    public String uid;


    @ColumnInfo(name = "shop_id")
    public String sid;

    public ShopOrder(@NonNull String postContent, @NonNull String userId, @NonNull String shopId){
        desc = postContent;
        uid = userId;
        sid = shopId;
        date = new Date();
    }

    public ShopOrder(){
    }
    public String getShopId(){return sid;}

    public String getUserId(){return uid;}

    public int getOrderId(){return oid;}

    public String getDesc(){return desc;}

}