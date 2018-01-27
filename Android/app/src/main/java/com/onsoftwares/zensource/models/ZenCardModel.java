package com.onsoftwares.zensource.models;

import com.onsoftwares.zensource.utils.httputil.IHttpResponseConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ZenCardModel implements IHttpResponseConverter<List<ZenCardModel>> {

    private int id;
    private String message;
    private String author;
    private String language;
    private String image64encoded;
    private Date createdOn;
    private int likes;
    private int dislikes;
    private List<String> tags;
    private boolean liked;
    private boolean disliked;

    public ZenCardModel() {
        tags = new ArrayList<String>();
    }

    public ZenCardModel(int id, String message, String author, String language, String image64encoded, Date createdOn, int likes, int dislikes, List<String> tags) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.language = language;
        this.image64encoded = image64encoded;
        this.createdOn = createdOn;
        this.likes = likes;
        this.dislikes = dislikes;
        this.tags = tags;
        this.liked = false;
        this.disliked = false;
    }

    public ZenCardModel(int id, String message, String author, String language, String image64encoded, int likes, int dislikes) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.language = language;
        this.image64encoded = image64encoded;
        this.likes = likes;
        this.dislikes = dislikes;
        this.disliked = false;
        this.liked = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getImage64encoded() {
        return image64encoded;
    }

    public void setImage64encoded(String image64encoded) {
        this.image64encoded = image64encoded;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean isDisliked() {
        return disliked;
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public void setLikedState(HashSet<String> likedIds, HashSet<String> dislikedIds) {
        String idStr = this.id + "";
        if (likedIds.contains(idStr) && !dislikedIds.contains(idStr)) this.liked    = true;
        if (dislikedIds.contains(idStr) && !likedIds.contains(idStr)) this.disliked = true;
    }

    @Override
    public List<ZenCardModel> convertHttpResponse(String response) throws JSONException {
        ArrayList<ZenCardModel> list = new ArrayList<>();
        JSONArray jsonArr = new JSONArray(response);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject j = jsonArr.getJSONObject(i);

            int id = j.getInt("id");
            String message = j.getString("message");
            String author = j.getString("author");
            String language = j.getString("language");
            String image64encoded = j.getString("image64Encoded");
            int likes = j.getInt("likes");
            int dislikes = j.getInt("dislikes");

            list.add(new ZenCardModel(id, message, author, language, image64encoded, likes, dislikes));

        }

        return list;
    }

    public static class SingleItemConverter implements IHttpResponseConverter<ZenCardModel> {
        @Override
        public ZenCardModel convertHttpResponse(String response) throws JSONException {
            JSONObject j = new JSONObject(response);

            int id = j.getInt("id");
            String message = j.getString("message");
            String author = j.getString("author");
            String language = j.getString("language");
            String image64encoded = j.getString("image64Encoded");
            int likes = j.getInt("likes");
            int dislikes = j.getInt("dislikes");

            return new ZenCardModel(id, message, author, language, image64encoded, likes, dislikes);

        }
    }

}
