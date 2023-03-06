package com.example.coffeetrail.model;

import androidx.lifecycle.ViewModel;

import java.util.UUID;

public class PostViewModel extends ViewModel {
    private String postContent;
    private UUID pid;

    public PostViewModel(){
        pid = UUID.randomUUID();
    }

    public void setPost(String post){
        postContent = post;
    }

    public String getPost(){
        return postContent;
    }
}
