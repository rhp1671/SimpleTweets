package com.codepath.apps.kennardtweets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.kennardtweets.models.Tweet;
import com.codepath.apps.kennardtweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static android.R.attr.editable;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.codepath.apps.kennardtweets.R.string.tweet;

/**
 * Created by raprasad on 3/25/17.
 */

public class ComposeTweetDialogFragment extends AppCompatDialogFragment {

    @BindView(R.id.ivClose)
    ImageView ivClose;
    @BindView(R.id.ivUser)
            ImageView ivUser;
    @BindView(R.id.tvTweetText)
    TextView tvTweet;
    @BindView(R.id.tvCharsLeft) TextView tvCharsLeft;
    @BindView(R.id.tvUserName) TextView tvUserName;
    @BindView(R.id.tvScreenUserName) TextView tvScreenName;
    @BindView(R.id.btnSend)
    Button btnSend;

    private static final int TWEET_LIMIT = 140;
    public static final String TAG = ComposeTweetDialogFragment.class.getName();

    public interface ComposeTweetDialogListener {
        void onFinishEditDialog(Tweet tweet);
    }

    private ComposeTweetDialogListener listener;


    public static ComposeTweetDialogFragment newInstance(Bundle args) {
        ComposeTweetDialogFragment frag = new ComposeTweetDialogFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ComposeTweetDialogListener) context;
        } catch (Exception ex) {

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tweet_fragment, container);
        ButterKnife.bind(this, v);
        tvCharsLeft.setText(String.valueOf(TWEET_LIMIT));
        tvTweet.addTextChangedListener(mTextEditorWatcher);
        btnSend.setEnabled(false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return v;
    }
    @OnClick(R.id.ivClose)
    public void close() {
        dismiss();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        User u = TwitterApp.getUser();
        if (u != null){
            tvUserName.setText(u.getName());
            tvScreenName.setText(u.getScreenName());
            Glide.with(getContext())
                    .load(u.getProfileImageUrl()).into(ivUser);
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
        }

        public void afterTextChanged(Editable s) {
            String tweet = s != null ? s.toString() : null;
            int len = tweet != null ? tweet.length() : 0;
            if (len > 0) {
                tvCharsLeft.setText(String.valueOf(TWEET_LIMIT-len));
                btnSend.setEnabled(true);
            } else {
                btnSend.setEnabled(false);
            }
        }
    };

    @OnClick(R.id.btnSend)
    public void postTweet(){
        TwitterClient client;
        client = TwitterApp.getRestClient();
        String tweet = tvTweet.getText().toString();
        if (tweet != null && !tweet.isEmpty()) {
            client.postTweet(tweet, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Tweet tweetObj = Tweet.fromJson(response);
                    Log.d(TAG, "===>" + statusCode + " :"  +tweetObj.getBody() );
                    listener.onFinishEditDialog(tweetObj);
                    dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.d("debug", errorResponse.toString());
                    listener.onFinishEditDialog(null);
                    Toast.makeText(getContext(), "Unable to Send Tweet!", Toast.LENGTH_SHORT);
                }
            });
        } else {
            dismiss();
        }
    }
}