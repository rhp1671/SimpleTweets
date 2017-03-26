package com.codepath.apps.kennardtweets.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raprasad on 3/24/17.
 */

public class User implements Parcelable{

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return "@"+name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            Log.d("DEBUG", e.getMessage());
        }
        return user;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(uid);
        dest.writeString(screenName);
        dest.writeString(profileImageUrl);
    }

    public User(Parcel source){

        name=source.readString();
        uid=source.readLong();
        screenName=source.readString();
        profileImageUrl=source.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }
    };

    public User(){}

        @Override
    public int describeContents() {
        return 0;
    }

}






