package com.example.coffeetrail.model;

import java.util.UUID;

public class Shop {
    private String mName;
    private UUID pid;

    public Shop(){
        pid = UUID.randomUUID();
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }
}
