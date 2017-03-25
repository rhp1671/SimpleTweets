package com.codepath.apps.kennardtweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by raprasad on 3/24/17.
 */

public class Tweet {

    private String body;
    private long uid;
    private String createdAt;
    private String retweetCt;
    private String favCt;
    private User user;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRetweetCt() {
        return retweetCt;
    }

    public String getFavCt() {
        return favCt;
    }

    public User getUser() {
        return user;
    }


    public static ArrayList fromJSONArray(JSONArray array){
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < array.length() ; i++){
            try {
                JSONObject j = array.getJSONObject(i);
                if (j != null){
                    Tweet t = Tweet.fromJson(j);
                    if (t != null){
                        tweets.add(t);
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.retweetCt = jsonObject.getString("retweet_count");
            tweet.favCt = jsonObject.getString("favorite_count");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }
}
