package com.codepath.apps.kennardtweets.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raprasad on 3/24/17.
 */

public class User {

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
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
}
