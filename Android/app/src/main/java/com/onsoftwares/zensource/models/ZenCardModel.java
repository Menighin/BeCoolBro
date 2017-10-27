package com.onsoftwares.zensource.models;

import com.onsoftwares.zensource.utils.httputil.IHttpResponseConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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
    }

    public ZenCardModel(int id, String message, String author, String language, String image64encoded, int likes, int dislikes) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.language = language;
        this.image64encoded = image64encoded;
        this.likes = likes;
        this.dislikes = dislikes;
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
}
