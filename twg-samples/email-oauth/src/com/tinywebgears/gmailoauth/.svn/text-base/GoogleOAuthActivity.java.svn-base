package com.tinywebgears.gmailoauth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tinywebgears.gmailoauth.util.OAuthBuilder;
import com.tinywebgears.gmailoauth.util.OAuthHelper;

public class GoogleOAuthActivity extends Activity
{
    private static final String TAG = "GoogleOAuthActivity";

    private static GoogleOAuthActivity instance;
    private WebView webview;
    private boolean waitingForResponse = false;

    public static GoogleOAuthActivity get()
    {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "GoogleOAuthActivity.onCreate");
        super.onCreate(savedInstanceState);
        instance = this;
        webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setVisibility(View.VISIBLE);

        setContentView(webview);
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                if (waitingForResponse && url.startsWith(OAuthHelper.OAUTH_CALLBACK_URL))
                {
                    waitingForResponse = false;
                    Intent oauthActivity = new Intent().setClass(getApplicationContext(), OAuthActivity.class);
                    oauthActivity.putExtra(OAuthActivity.PARAM_OAUTH_RESULT, url);
                    startActivity(oauthActivity);
                }
                System.out.println("onPageFinished : " + url);
            }
        });

        GetAuthorisationUrlTask getAuthorisationUrlTask = new GetAuthorisationUrlTask(this,
                new GetAuthorisationUrlTaskCallback()
                {
                    @Override
                    public void urlCreated(String url)
                    {
                        waitingForResponse = true;
                        webview.loadUrl(url);
                    }
                });
        getAuthorisationUrlTask.execute();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.d(TAG, "GoogleOAuthActivity.onNewIntent");
        super.onNewIntent(intent);

    }

    @Override
    protected void onStop()
    {
        Log.d(TAG, "GoogleOAuthActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "GoogleOAuthActivity.onResume");
        super.onResume();
    }

    private static interface GetAuthorisationUrlTaskCallback
    {
        void urlCreated(String url);
    }

    private class GetAuthorisationUrlTask extends AsyncTask<String, Void, String>
    {
        private GetAuthorisationUrlTaskCallback callback;

        public GetAuthorisationUrlTask(Context context, GetAuthorisationUrlTaskCallback callback)
        {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                Log.i(TAG, "Getting authorization url...");
                return OAuthBuilder.get().getAuthorizationUrl();
            }
            catch (Exception e)
            {
                Log.e(TAG, "Unable to get OAuth authorization url: " + e, e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.i(TAG, "Authorisation url: " + result);
            if (result == null)
                Toast.makeText(getApplicationContext(), "Error getting authorization url.", Toast.LENGTH_SHORT).show();
            else
                callback.urlCreated(result);
        }
    }
}
