package com.tinywebgears.gmailoauth;

import org.scribe.model.Token;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tinywebgears.gmailoauth.mail.LocalEmailService;

public class UserData
{
    private static volatile SharedPreferences prefs;

    public static void setPrefs(SharedPreferences p)
    {
        prefs = p;
    }

    private static SharedPreferences getPrefs()
    {
        return prefs;
    }

    public static SharedPreferences getPrefs(Context context)
    {
        synchronized (UserData.class)
        {
            if (prefs != null)
                return prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs;
        }
    }

    public static boolean isEmailSetUp()
    {
        return getPrefs() != null && getPrefs().getString(UserData.PREF_KEY_TARGET_EMAIL_ADDRESS, null) != null;
    }

    public static boolean isEmailServiceAvailable()
    {
        return LocalEmailService.get() != null;
    }

    public static boolean isOAuthSetUp()
    {
        return getPrefs() != null && getPrefs().getString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN, null) != null
                && getPrefs().getString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN_SECRET, null) != null;
    }

    public static Token getAccessToken()
    {
        if (!UserData.isOAuthSetUp())
            return null;
        String accessToken = prefs.getString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN, null);
        String accessTokenSecret = prefs.getString(UserData.PREF_KEY_OAUTH_ACCESS_TOKEN_SECRET, null);
        return new Token(accessToken, accessTokenSecret);
    }

    public static final String PREF_KEY_OAUTH_ACCESS_TOKEN = "OAUTH_TOKEN";
    public static final String PREF_KEY_OAUTH_ACCESS_TOKEN_SECRET = "OAUTH_TOKEN_SECRET";
    public static final String PREF_KEY_OAUTH_EMAIL_ADDRESS = "OAUTH_EMAIL_ADDRESS";
    public static final String PREF_KEY_TARGET_EMAIL_ADDRESS = "TARGET_EMAIL_ADDRESS";
}
