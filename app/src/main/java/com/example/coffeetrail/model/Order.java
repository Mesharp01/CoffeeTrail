package com.example.coffeetrail.model;
import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import java.util.Date;

@Fts4
@Entity(foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "uid",
                        childColumns = "user_id"),
                @ForeignKey(entity = Shop.class,
                        parentColumns = "sid",
                        childColumns = "shop_id")
        })

public class Order {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "oid")
    public int mUid;

    @NonNull
    @ColumnInfo(name = "description")
    public String mDesc;

    @NonNull
    @ColumnInfo(name = "date")
    public Date date;

    @NonNull
    @ColumnInfo(name = "user_id")
    public int uid;

    @NonNull
    @ColumnInfo(name = "shop_id")
    public int sid;
}