package com.jekos.dddwatcher.models;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by жекос on 21.08.2017.
 */

public class Shot {
    private int id;
    private String title;
    private String description;
    private int views_count;
    private int likes_count;
    private int comments_count;
    private Date updated_at;
    private String html_url;
    private List<String> tags;
    private HashMap<String,String> images;
    private boolean animated;
    private User user;

    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getViews_count() {
        return views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public List<String> getTags() {
        return tags;
    }

    public HashMap<String, String> getImages() {
        return images;
    }

    public User getUser() {
        return user;
    }

    public boolean isAnimated() {
        return animated;
    }
}
