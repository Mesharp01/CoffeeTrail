package com.example.coffeetrail.model;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Order.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE))
public class OrderRepository {

}
