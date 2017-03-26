package com.codepath.apps.kennardtweets;

/**
 * Created by raprasad on 3/25/17.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.bumptech.glide.Glide;
import com.codepath.apps.kennardtweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;
import static android.R.attr.orientation;
import static android.R.attr.type;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by raprasad on 3/24/17.
 */

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Tweet> mTweets;
    public static final String TAG = TimelineRecyclerAdapter.class.getName();
    public static final int BASIC_TWEET = 0;

    public static class BasicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivUser)
        ImageView imageView;
        @BindView(R.id.tvTweet)
        TextView tvTweet;
        @BindView(R.id.tvScreenUserName) TextView tvScreenUserName;
        @BindView(R.id.tvUserName) TextView tvUserName;

        public BasicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void renderTweet(Tweet tweet){
            imageView.setImageResource(0);
            if (tweet != null) {
                tvScreenUserName.setText(tweet.getUser().getScreenName());
                tvUserName.setText(tweet.getUser().getName());
                tvTweet.setText(tweet.getBody());

                Glide.with(imageView.getContext())
                        .load(tweet.getUser().getProfileImageUrl()).into(imageView);

            }
        }

    }
    public TimelineRecyclerAdapter(Context context, ArrayList<Tweet> tweets) {
        this.mTweets = tweets;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return this.mTweets != null ? this.mTweets.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet = mTweets.get(position);
        return BASIC_TWEET;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BASIC_TWEET) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
            return new BasicViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof BasicViewHolder) {
                ((BasicViewHolder) holder).renderTweet(mTweets.get(position));
            }
        }
    }

    public void add(ArrayList<Tweet> tweet){
        mTweets.addAll(tweet);
        notifyDataSetChanged();

    }

    public void addTweet(Tweet tweet){
        mTweets.add(0, tweet);
        notifyDataSetChanged();
    }

    public void swap(ArrayList<Tweet> tweet){
        mTweets.clear();
        mTweets.addAll(tweet);
        notifyDataSetChanged();
    }
}

