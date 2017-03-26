package com.codepath.apps.kennardtweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.apps.kennardtweets.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "GvAcIJWxhmREMBHtWhp8xrNdT";       // Change this
    public static final String REST_CONSUMER_SECRET = "XJMNAgdfTmh7I75pIyrbL0DnKRPIr8VcuDCeSbknvtR2EXU2hw"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://kennardcb"; // Change this (here and in manifest)
    public static final String  POST_TWEET = "statuses/update.json";
    public static final String GET_TWEETS = "statuses/home_timeline.json";
    public static final String GET_USER = "account/verify_credentials.json";

    public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    public void getHomeTimeline(long maxID, long sinceID, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl(GET_TWEETS);
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (sinceID != 0) {
            params.put("since_id", sinceID);
        }
        if (maxID != 0) {
            params.put("max_id", maxID);
        }
        client.get(apiUrl, params, handler);
    }

    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl(POST_TWEET);
        if (tweet != null && !tweet.isEmpty()){
            RequestParams params = new RequestParams();
            params.put("status", tweet);
            client.post(apiUrl, params, handler);
        }
    }

    public void getCurrentUser(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl(GET_USER);
        RequestParams params = new RequestParams();
        client.get(apiUrl, params, handler);
    }
}
