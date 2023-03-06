package com.example.coffeetrail.model;

import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class OrderViewModel extends ViewModel {
    private String postContent;
    private UUID pid;

    public OrderViewModel(){
        pid = UUID.randomUUID();
    }

    public void setPost(String post){
        postContent = post;
    }

    public String getPost(){
        return postContent;
    }
}
