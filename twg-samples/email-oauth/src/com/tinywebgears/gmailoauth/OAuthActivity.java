package com.tinywebgears.gmailoauth;

import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.model.Token;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tinywebgears.gmailoauth.util.OAuthBuilder;
import com.tinywebgears.gmailoauth.util.OAuthHelper;

public class OAuthActivity extends Activity
{
    private static final String TAG = "OAuthActivity";
    public static final String PARAM_OAUTH_RESULT = "result";

    private static OAuthActivity instance;
    private SharedPreferences prefs;
    private TextView oauthStatusLabel;

    public static OAuthActivity get()
    {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "OAuthActivity.onCreate");
        super.onCreate(savedInstanceState);
        instance = this;
        prefs = UserData.getPrefs(this);

        setContentView(R.layout.oauth);

        final Button oauthGoogle = (Button) this.findViewById(R.id.oauthgoogle);
        oauthGoogle.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                handleOAuthGoogleButton();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.d(TAG, "OAuthActivity.onNewIntent");
        super.onNewIntent(intent);

        final Button returnMain = (Button) this.findViewById(R.id.returnmain);
        returnMain.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                handleReturnMainButton();
            }
        });

        final String uri = intent.getStringExtra(PARAM_OAUTH_RESULT);
        if (uri != null)
        {
            if (OAuthBuilder.get().extractVerifier(uri) != null)
            {
                Log.i(TAG, "Retrieving access token from url: " + uri);
                GetAccessTokenTask getAccessTokenTask = new GetAccessTokenTask();
                getAccessTokenTask.execute(uri);
            }
            else
                oauthStatusLabel.setText(R.string.oauth_error);
        }
    }

    @Override
    protected void onStop()
    {
        Log.d(TAG, "OAuthActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "OAuthActivity.onResume");
        super.onResume();
    }

    private void handleOAuthGoogleButton()
    {
        setContentView(R.layout.oauth_result);
        oauthStatusLabel = (TextView) findViewById(R.id.authstatus);
        oauthStatusLabel.setText(R.string.please_wait);
        startActivity(new Intent().setClass(getApplicationContext(), GoogleOAuthActivity.class));
    }

    private void handleReturnMainButton()
    {
        returnToMainPage();
    }

    private void returnToMainPage()
    {
        startActivity(new Intent().setClass(getApplicationContext(), MainActivity.class));
    }

    private class GetAccessTokenTask extends AsyncTask<String, Void, Boolean>
    {
        private String findEmailAddress() throws Exception
        {
            try
            {
                String jsonOutput = OAuthBuilder.get().makeSecuredRequest(UserData.getAccessToken(),
                        OAuthHelper.URL_GET_EMAIL);
                JSONObject jsonResponse = new JSONObject(jsonOutput);
                String email = jsonResponse.getString("email");
                Log.i(TAG, "Email address found: " + email);
                return email;
            }
            catch (JSONException e)
            {
                throw new Exception("Invalid response to user details API: " + e.getMessage(), e);
            }
        }

        private void getAccessToken(String uri) throws Exception
        {
            String verifier = OAuthBuilder.get().extractVerifier(uri);
            Token accessToken = OAuthBuilder.get().getAccessToken(verifier);
            Log.d(TAG, "Access token: " + accessToken);
            final Editor edit = prefs.edit();
            edit.putString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN, accessToken.getToken());
            edit.putString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN_SECRET, accessToken.getSecret());
            edit.commit();

            String emailAddress = findEmailAddress();
            Log.d(TAG, "Email: " + emailAddress);
            edit.putString(UserData.PREF_KEY_OAUTH_EMAIL_ADDRESS, emailAddress);
            edit.commit();
        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            Log.i(TAG, "Getting access token: " + params);
            if (params.length < 1)
            {
                Log.wtf(TAG, "Insufficient parameters: " + params);
                return false;
            }
            String url = params[0];
            try
            {
                getAccessToken(url);
                return true;
            }
            catch (Exception e)
            {
                Log.e(TAG, "Unable to get an OAuth access token: " + e, e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            Log.i(TAG, "Access token retrieval result: " + result);
            if (result)
                oauthStatusLabel.setText(R.string.oauth_success);
            else
                oauthStatusLabel.setText(R.string.oauth_error);
        }
    }
}
