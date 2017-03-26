package com.codepath.apps.kennardtweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by raprasad on 3/24/17.
 */

public class Tweet implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeString(createdAt);
        dest.writeString(retweetCt);
        dest.writeString(favCt);
        dest.writeParcelable(this.user, flags);
    }

    public Tweet(Parcel source){

        body=source.readString();
        uid=source.readLong();
        createdAt=source.readString();
        retweetCt=source.readString();
        favCt = source.readString();
        user = (User)source.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }

        @Override
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }
    };

    public Tweet(){}

}







