package com.example.socialmediaaggregatorapp.Models;

import androidx.annotation.NonNull;

public class Post {

    public enum socialMediaType {
        facebook, instagram, twitter
    };

    private long id;
    private String postImage;
    private int numberOfLikes;
    private socialMediaType type;
    private String date;
    private Integer numberOfComments;
    private Integer numberOfRetweets;
    private String username;
    private String profileImage;
    private String postDescription;

    public Post() {

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public socialMediaType getType() {
        return type;
    }

    public void setType(socialMediaType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Integer getNumberOfRetweets() {
        return numberOfRetweets;
    }

    public void setNumberOfRetweets(Integer numberOfRetweets) {
        this.numberOfRetweets = numberOfRetweets;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", postImage='" + postImage + '\'' +
                ", numberOfLikes=" + numberOfLikes +
                ", type=" + type +
                ", date='" + date + '\'' +
                ", numberOfComments=" + numberOfComments +
                ", numberOfRetweets=" + numberOfRetweets +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", postDescription='" + postDescription + '\'' +
                '}';
    }
}
