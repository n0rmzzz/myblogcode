package com.tinywebgears.gmailoauth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tinywebgears.gmailoauth.mail.LocalEmailService;
import com.tinywebgears.gmailoauth.mail.LocalEmailService.EmailTaskCallback;

public class MainActivity extends Activity
{
    private static final String TAG = "MainActivity";

    private static MainActivity instance;
    private SharedPreferences prefs;
    private EditText targetEmail;
    private TextView oauthResult;
    private EditText oauthEmail;
    private Button sendEmail;

    public static MainActivity get()
    {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "MainActivity.onCreate");
        super.onCreate(savedInstanceState);
        instance = this;
        prefs = UserData.getPrefs(this);
        setContentView(R.layout.main);
        targetEmail = (EditText) MainActivity.this.findViewById(R.id.emailtextbox);
        oauthResult = (TextView) findViewById(R.id.oauthresult);
        oauthEmail = (EditText) findViewById(R.id.oauthemailtextbox);

        sendEmail = (Button) this.findViewById(R.id.saveemailbutton);
        sendEmail.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                handleSendEmailButton();
            }
        });

        final Button configureOAuth = (Button) this.findViewById(R.id.configoauthbutton);
        configureOAuth.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                handleConfigureOAuthButton();
            }
        });

        final Button clearOAuth = (Button) this.findViewById(R.id.clearoauthbutton);
        clearOAuth.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                handleClearOAuthButton();
            }
        });
    }

    @Override
    protected void onStart()
    {
        Log.d(TAG, "MainActivity.onStart");
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        Log.d(TAG, "MainActivity.onNewIntent");
        super.onNewIntent(intent);
    }

    private void initUI()
    {
        Log.d(TAG, "MainActivity.initUI");
        if (UserData.isOAuthSetUp())
        {
            oauthResult.setText(R.string.oauth_set_up);
            oauthEmail.setText(prefs.getString(UserData.PREF_KEY_OAUTH_EMAIL_ADDRESS, null));
        }
        else
        {
            oauthResult.setText(R.string.oauth_not_set_up);
            oauthEmail.setText(null);
        }

        String emailAddress = prefs.getString(UserData.PREF_KEY_TARGET_EMAIL_ADDRESS, null);
        if (emailAddress != null && emailAddress.length() > 0)
            targetEmail.setText(emailAddress);
        else
            targetEmail.setText(null);
    }

    @Override
    protected void onStop()
    {
        Log.d(TAG, "MainActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "MainActivity.onResume");
        super.onResume();
        startService(new Intent(this, LocalEmailService.class));
        initUI();
    }

    private void saveTargetEmail(String emailAddress)
    {
        final Editor edit = prefs.edit();
        edit.putString(UserData.PREF_KEY_TARGET_EMAIL_ADDRESS, emailAddress);
        edit.commit();
        Log.i(TAG, "Email address saved: " + emailAddress);
    }

    private void clearCredentials()
    {
        final Editor edit = prefs.edit();
        edit.remove(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN);
        edit.remove(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN_SECRET);
        edit.remove(UserData.PREF_KEY_OAUTH_EMAIL_ADDRESS);
        edit.commit();
        Log.i(TAG, "OAuth cleared.");
    }

    private void handleSendEmailButton()
    {
        saveTargetEmail(targetEmail.getText().toString());
        LocalEmailService.get().sendEmail("Email OAuth Sample", "This is a test email!", new EmailTaskCallback()
        {
            @Override
            public void emailTaskDone(Boolean result, String errorMessage)
            {
                Log.i(TAG, "Email test result: " + result + " error message: " + errorMessage);
                Toast.makeText(MainActivity.this, "Test email sent to " + targetEmail.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleConfigureOAuthButton()
    {
        Log.i(TAG, "Configuring OAuth...");

        startActivity(new Intent().setClass(getApplicationContext(), OAuthActivity.class));
    }

    private void handleClearOAuthButton()
    {
        clearCredentials();
        initUI();
    }
}
