package com.codepath.apps.kennardtweets.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.kennardtweets.R;
import com.codepath.apps.kennardtweets.adapters.TimelineRecyclerAdapter;
import com.codepath.apps.kennardtweets.TwitterApp;
import com.codepath.apps.kennardtweets.databinding.ActivityTimelineBinding;
import com.codepath.apps.kennardtweets.network.TwitterClient;
import com.codepath.apps.kennardtweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.kennardtweets.R.id.lvTweets;

public class TimelineActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogListener {

    private TwitterClient client;
    private TimelineRecyclerAdapter mArrayAdapter;
    private ArrayList<Tweet> mTweets;
    public static final String TAG = TimelineRecyclerAdapter.class.getName();
    private ActivityTimelineBinding binding;
  //  @BindView(R.id.lvTweets)
    RecyclerView lvTweets;
   // @BindView(R.id.fabCompose)
    FloatingActionButton btnCompose;
    private EndlessRecyclerViewScrollListener scrollListener;
    LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeContainer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timeline);
       // setContentView(R.layout.activity_timeline);
       // ButterKnife.bind(this);
        lvTweets = binding.lvTweets;
        btnCompose = binding.fabCompose;
        mTweets = new ArrayList<>();
        mArrayAdapter = new TimelineRecyclerAdapter(this, mTweets);
        lvTweets.setAdapter(mArrayAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        lvTweets.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        lvTweets.addItemDecoration(itemDecoration);

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


        btnCompose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ComposeTweetDialogFragment composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance(null);
                composeTweetDialogFragment.show(fm, "compose_tweet");

            }
        });


        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title));

        populateTimeline(0,1);

        swipeContainer = binding.swipeContainer;
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_blue_dark,
                android.R.color.holo_blue_bright,
                android.R.color.holo_blue_light);

        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);
            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mTweets.size() < 1) {
                    populateTimeline(0, 1);
                } else {
                    populateTimeline(mTweets.get(mTweets.size() - 1).getUid(), 0);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*  if (item.getItemId() == R.id.menuTweet){
            FragmentManager fm = getSupportFragmentManager();
            ComposeTweetDialogFragment composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance(null);
            composeTweetDialogFragment.show(fm, "compose_tweet");

        }*/
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
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("debug", errorResponse.toString());
                swipeContainer.setRefreshing(false);
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
