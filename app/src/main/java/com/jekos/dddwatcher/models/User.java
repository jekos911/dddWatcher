package com.jekos.dddwatcher.models;

/**
 * Created by жекос on 21.08.2017.
 */

public class User {
    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;
    private String followers_count;
    private String followings_count;
    private String likes_count;
    private String likes_received_count;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public String getLocation() {
        return location;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public String getFollowings_count() {
        return followings_count;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public String getLikes_received_count() {
        return likes_received_count;
    }
}
