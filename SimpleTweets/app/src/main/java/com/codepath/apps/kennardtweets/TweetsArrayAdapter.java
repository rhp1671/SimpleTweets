package com.codepath.apps.kennardtweets;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.kennardtweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.orientation;
import static android.R.attr.type;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by raprasad on 3/24/17.
 */

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private Context mContext;

    public static class ViewHolder {
        @BindView(R.id.ivUser)
        ImageView imageView;
        @BindView(R.id.tvTweet)
        TextView tvTweet;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }




    public TweetsArrayAdapter(Context context, ArrayList<Tweet> tweets) {
        super(context, 0, tweets);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = (Tweet) getItem(position);

        final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.imageView.setImageResource(0);

        if (tweet != null) {
            viewHolder.tvTweet.setText(tweet.getBody());

            Glide.with(mContext)
                    .load(tweet.getUser().getProfileImageUrl()).into(viewHolder.imageView);

        }
        return convertView;
    }
}
