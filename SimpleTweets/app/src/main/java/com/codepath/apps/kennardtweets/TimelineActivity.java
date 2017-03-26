package com.codepath.apps.kennardtweets;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.kennardtweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static android.R.attr.x;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.codepath.apps.kennardtweets.R.string.tweet;
import static com.codepath.apps.kennardtweets.models.Tweet.fromJSONArray;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogListener {

    private TwitterClient client;
    private TimelineRecyclerAdapter mArrayAdapter;
    private ArrayList<Tweet> mTweets;
    public static final String TAG = TimelineRecyclerAdapter.class.getName();

    @BindView(R.id.lvTweets)
    RecyclerView lvTweets;
    private EndlessRecyclerViewScrollListener scrollListener;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        mTweets = new ArrayList<>();
        mArrayAdapter = new TimelineRecyclerAdapter(this, mTweets);
        lvTweets.setAdapter(mArrayAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        lvTweets.setLayoutManager(linearLayoutManager);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code     is needed to append new items to the bottom of the list
                final int itemCt = totalItemsCount;
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        populateTimeline(mTweets.get(itemCt - 1).getUid(), 0);
                    }
                });



            }

        };
        // Adds the scroll listener to RecyclerView
        lvTweets.addOnScrollListener(scrollListener);




        client = TwitterApp.getRestClient();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));

        populateTimeline(0,1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuTweet){
            FragmentManager fm = getSupportFragmentManager();
            ComposeTweetDialogFragment composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance(null);
            composeTweetDialogFragment.show(fm, "compose_tweet");

        }
        return super.onOptionsItemSelected(item);
    }

    private void populateTimeline(final long maxId, long sinceID){

        client.getHomeTimeline(maxId, sinceID, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList a = Tweet.fromJSONArray(response);
                if (maxId == 0){
                    mArrayAdapter.swap(a);
                } else {
                    mArrayAdapter.add(a);
                }
                Log.d("debug", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("debug", errorResponse.toString());
            }
        });

    }

    @Override
    public void onFinishEditDialog(Tweet result) {
        if (result != null) {
            mArrayAdapter.addTweet(result);
            linearLayoutManager.scrollToPosition(0);
        }
    }
}
