package com.tinywebgears.gmailoauth.mail;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;
import org.scribe.utils.OAuthEncoder;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import com.tinywebgears.gmailoauth.R;
import com.tinywebgears.gmailoauth.UserData;
import com.tinywebgears.gmailoauth.util.OAuthBuilder;
import com.tinywebgears.gmailoauth.util.OAuthHelper;

public class LocalEmailService extends Service
{
    private static final String TAG = "LocalEmailService";

    private static LocalEmailService instance;

    public static LocalEmailService get()
    {
        return instance;
    }

    private SharedPreferences prefs;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        prefs = UserData.getPrefs(this);
        Log.d(TAG, "LocalEmailService: onCreate");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "LocalEmailService: onDestroy");
    }

    public boolean isSetUp()
    {
        return prefs.getString(UserData.PREF_KEY_TARGET_EMAIL_ADDRESS, null) != null;
    }

    public void sendEmail(String subject, String text, EmailTaskCallback callback)
    {
        if (!UserData.isEmailSetUp())
        {
            Log.e(TAG, "Please set the target email address.");
            return;
        }
        if (!UserData.isOAuthSetUp())
        {
            Log.e(TAG, "Please configure email send method.");
            return;
        }
        String xoauthString = buildXOAuth();
        Log.d(TAG, "XOAuth: " + xoauthString);
        String fromEmail = prefs.getString(UserData.PREF_KEY_OAUTH_EMAIL_ADDRESS, null);
        EmailSendTask emailSendTask = new EmailSendTask(fromEmail, xoauthString, callback);
        String targetEmailAddress = prefs.getString(UserData.PREF_KEY_TARGET_EMAIL_ADDRESS, null);
        emailSendTask.execute(targetEmailAddress, subject, text);
    }

    public interface EmailTaskCallback
    {
        void emailTaskDone(Boolean result, String errorMessage);
    }

    private static class EmailTaskResponseType extends Pair<Boolean, String>
    {
        private EmailTaskResponseType(Boolean first, String second)
        {
            super(first, second);
        }

        public static EmailTaskResponseType create(Boolean first, String second)
        {
            return new EmailTaskResponseType(first, second);
        }

        public String toString()
        {
            return "" + first + ":" + second;
        }
    }

    private class EmailSendTask extends AsyncTask<String, Void, EmailTaskResponseType>
    {
        private final String fromEmail;
        private final String xoauthString;
        private final EmailTaskCallback callback;

        public EmailSendTask(String fromEmail, String xoauthString, EmailTaskCallback callback)
        {
            this.fromEmail = fromEmail;
            this.xoauthString = xoauthString;
            this.callback = callback;
        }

        @Override
        protected EmailTaskResponseType doInBackground(String... params)
        {
            Log.i(TAG, "Sending email: " + params);
            if (params.length < 3)
            {
                Log.wtf(TAG, "Insufficient parameters: " + params);
                return EmailTaskResponseType.create(false,
                        getString(R.string.error_internal, "Insufficient parameters to email task."));
            }
            String email = params[0];
            String subject = params[1];
            String text = params[2];

            try
            {
                OAuthGMailSender sender = new OAuthGMailSender(xoauthString);
                sender.sendMail(subject, text, fromEmail, email);
                return EmailTaskResponseType.create(true, null);
            }
            catch (Exception e)
            {
                Log.e(TAG, "An error occurred while sending email: " + e, e);
                return EmailTaskResponseType.create(false, "Error sending email: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(EmailTaskResponseType result)
        {
            Log.i(TAG, "Email sent, result: " + result);
            callback.emailTaskDone(result.first, result.second);
        }
    }

    private String buildXOAuth()
    {
        String fromEmail = prefs.getString(UserData.PREF_KEY_OAUTH_EMAIL_ADDRESS, null);
        String url = String.format(OAuthHelper.URL_SMTP_AUTH, fromEmail);
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        OAuthBuilder.get().signRequest(UserData.getAccessToken(), request);

        StringBuilder sb = new StringBuilder();
        sb.append("GET ");
        sb.append(url);
        sb.append(" ");
        Set<String> requiredParams = new HashSet<String>(Arrays.asList(OAuthConstants.NONCE, OAuthConstants.TIMESTAMP,
                OAuthConstants.SCOPE, OAuthConstants.SIGN_METHOD, OAuthConstants.SIGNATURE, OAuthConstants.VERIFIER,
                OAuthConstants.TOKEN, OAuthConstants.VERSION, OAuthConstants.CONSUMER_KEY));
        int i = 0;
        for (Entry<String, String> entry : request.getOauthParameters().entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!requiredParams.contains(key))
                continue;

            if (i++ != 0)
                sb.append(",");
            sb.append(key);
            sb.append("=\"");
            sb.append(OAuthEncoder.encode(value));
            sb.append("\"");
        }
        Log.d(TAG, "xoauth encoding " + sb);

        Base64 base64 = new Base64();
        try
        {
            byte[] buf = base64.encode(sb.toString().getBytes("utf-8"));
            return new String(buf, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e(TAG, "Invalid XOAUTH string: " + sb, e);
        }
        return null;
    }
}
