package com.codepath.apps.kennardtweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.kennardtweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetsArrayAdapter mArrayAdapter;
    private ArrayList<Tweet> tweets;

    @BindView(R.id.lvTweets) ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        tweets = new ArrayList<>();
        mArrayAdapter = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(mArrayAdapter);
        client = TwitterApp.getRestClient();
        populateTimeline();
    }

    private void populateTimeline(){

        client.getHomeTimeline( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mArrayAdapter.addAll(Tweet.fromJSONArray(response));
                Log.d("debug", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("debug", errorResponse.toString());
            }
        });

    }
}
