package com.codepath.apps.kennardtweets.adapters;

/**
 * Created by raprasad on 3/25/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.codepath.apps.kennardtweets.R;
import com.codepath.apps.kennardtweets.utilities.Utils;
import com.codepath.apps.kennardtweets.models.Tweet;

/**
 * Created by raprasad on 3/24/17.
 */

public class TimelineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Tweet> mTweets;
    public static final String TAG = TimelineRecyclerAdapter.class.getName();
    public static final int BASIC_TWEET = 0;
    public static final int PHOTO_TWEET = 1;

    public class BasicViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivUser)
        ImageView imageView;
        @BindView(R.id.tvTweet)
        TextView tvTweet;
        @BindView(R.id.tvScreenUserName) TextView tvScreenUserName;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvCreatedDate) TextView tvCreatedDate;

        public BasicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        public void renderTweet(Tweet tweet){
            imageView.setImageResource(0);
            if (tweet != null) {
                tvScreenUserName.setText(tweet.getUser().getScreenName());
                tvUserName.setText(tweet.getUser().getName());
                tvTweet.setText(fromHtml(tweet.getBody()));
                tvCreatedDate.setText(Utils.getRelativeTimeAgo(tweet.getCreatedAt()));
                Log.d(TAG, "===>" + tweet.getMediaType() + " :" + tweet.getMediaUrl());
                Glide.with(imageView.getContext())
                        .load(tweet.getUser().getProfileImageUrl()).into(imageView);

            }
        }
    }

    public class PhotoViewHolder extends BasicViewHolder {
        @BindView(R.id.ivPhoto)
        ImageView imageView;

        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void renderTweet(Tweet tweet) {
            super.renderTweet(tweet);

            Glide.with(imageView.getContext())
                    .load(tweet.getMediaUrl()).into(imageView);
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
        if (tweet.getMediaUrl() != null && !tweet.getMediaUrl().isEmpty()) {
            return PHOTO_TWEET;
        }
        return BASIC_TWEET;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BASIC_TWEET) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item, parent, false);
            return new BasicViewHolder(view);
        } else if (viewType == PHOTO_TWEET) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_list_item_photo, parent, false);
            return new PhotoViewHolder(view);
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

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}

