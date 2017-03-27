package com.codepath.apps.kennardtweets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.apps.kennardtweets.R;
import com.codepath.apps.kennardtweets.TwitterApp;
import com.codepath.apps.kennardtweets.models.User;
import com.codepath.apps.kennardtweets.network.TwitterClient;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

    public static final String TAG = LoginActivity.class.getName();
    @BindView(R.id.btnOauth)
    Button btnOauth;
    @BindView(R.id.pbProfile)
    ProgressBar pbProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
        btnOauth.setVisibility(View.INVISIBLE);
        pbProfile.setVisibility(View.VISIBLE);
        Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
        startActivity(i);

        TwitterClient client = TwitterApp.getRestClient();
        client.getCurrentUser( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                User u = User.fromJSON(response);
                if (u != null){
                    Log.d(TAG, u.toString());
                    TwitterApp.setUser(u);
                }
                btnOauth.setVisibility(View.VISIBLE);
                pbProfile.setVisibility(View.INVISIBLE);
                Intent i = new Intent(getApplicationContext(), TimelineActivity.class);
                startActivity(i);

                Log.d("===>debug", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                btnOauth.setVisibility(View.VISIBLE);
                pbProfile.setVisibility(View.INVISIBLE);
                if (errorResponse !=  null) {
                    Log.d("debug", errorResponse.toString());
                }
            }
        });



        Toast.makeText(getApplicationContext(), "Success!!", Toast.LENGTH_SHORT);
    }

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
