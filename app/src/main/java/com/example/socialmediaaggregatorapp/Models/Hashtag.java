package com.example.socialmediaaggregatorapp.Models;

public class Hashtag {
    private String name;
    private String query;
    private int tweet_volume;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getTweet_volume() {
        return tweet_volume;
    }

    public void setTweet_volume(int tweet_volume) {
        this.tweet_volume = tweet_volume;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "name='" + name + '\'' +
                ", query='" + query + '\'' +
                ", tweet_volume=" + tweet_volume +
                '}';
    }
}
