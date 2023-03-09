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
                        parentColumns = "rowid",
                        childColumns = "user_id"),
                @ForeignKey(entity = CoffeeShop.class,
                        parentColumns = "rowid",
                        childColumns = "shop_id")
        })
public class ShopOrder implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    public int oid;

    @NonNull
    @ColumnInfo(name = "description")
    public String desc;

    @NonNull
    @ColumnInfo(name = "date")
    public Date date;

    @NonNull
    @ColumnInfo(name = "user_id")
    public int uid;

    @NonNull
    @ColumnInfo(name = "shop_id")
    public int sid;

    public ShopOrder(@NonNull String postContent, @NonNull int userId, @NonNull int shopId){
        desc = postContent;
        uid = userId;
        sid = shopId;
        date = new Date();
    }

    public ShopOrder(){
    }
    public int getShopId(){return sid;}

    public int getUserId(){return uid;}

    public int getOrderId(){return oid;}

    public String getDesc(){return desc;}

}