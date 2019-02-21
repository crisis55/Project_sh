package com.example.surreal.shotpingdef.Model;

public class Post {
    private String postid;
    private String conditions_description;
    private String prize_description;
    private String publisher;


    public Post(String postid, String postimage, String conditions_description, String prize_description, String publisher) {
        this.postid = postid;
        this.conditions_description = conditions_description;
        this.prize_description = prize_description;
        this.publisher = publisher;

    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getConditions_description() {
        return conditions_description;
    }

    public void setConditions_description(String conditions_description) {
        this.conditions_description = conditions_description;
    }

    public String getPrize_description() {
        return prize_description;
    }

    public void setPrize_description(String prize_description) {
        this.prize_description = prize_description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}

