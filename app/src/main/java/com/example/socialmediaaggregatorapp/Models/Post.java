package com.example.socialmediaaggregatorapp.Models;

public class Post {
    private int id;
    private String username;
    private String profileImage;
    private String postDescription;
    private String postImage;
    private int numberOfLikes;
    private int numberOfComments;
    private enum socialMediaType {
        facebook, instagram, twitter
    };
    private String date;

    public Post() {

    }
}
