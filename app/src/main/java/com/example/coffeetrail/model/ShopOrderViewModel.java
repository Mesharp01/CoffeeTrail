package com.example.coffeetrail.model;

import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class ShopOrderViewModel extends ViewModel {
    private String postContent;
    private UUID pid;

    public ShopOrderViewModel(){
        pid = UUID.randomUUID();
    }

    public void setPost(String post){
        postContent = post;
    }

    public String getPost(){
        return postContent;
    }
}
