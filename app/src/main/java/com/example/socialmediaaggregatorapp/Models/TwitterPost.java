package com.example.socialmediaaggregatorapp.Models;

public class TwitterPost extends Post{
    public int numberOfRetweets;

    public TwitterPost() {
    }

    public int getNumberOfRetweets() {
        return numberOfRetweets;
    }

    public void setNumberOfRetweets(int numberOfRetweets) {
        this.numberOfRetweets = numberOfRetweets;
    }
}
