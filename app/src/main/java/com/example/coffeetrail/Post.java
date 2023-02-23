package com.example.coffeetrail;

import java.util.UUID;

public class Post {
    private String postContent;
    private UUID pid;

    public Post(){
        pid = UUID.randomUUID();
    }

    public void setPost(String post){
        postContent = post;
    }

    public String getPost(){
        return postContent;
    }
}
