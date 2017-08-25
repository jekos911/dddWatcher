package com.jekos.dddwatcher.models;

/**
 * Created by жекос on 24.08.2017.
 */

public class Team {
    int id;
    String name;
    String username;
    String html_url;
    String avatar_url;
    String bio;
    String location;

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
}
