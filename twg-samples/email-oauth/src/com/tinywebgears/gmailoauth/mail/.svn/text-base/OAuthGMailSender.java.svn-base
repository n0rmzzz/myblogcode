package com.tinywebgears.gmailoauth.mail;

import java.io.PrintWriter;
import java.io.Writer;
import java.security.AccessController;
import java.security.Provider;
import java.security.Security;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import android.util.AndroidRuntimeException;
import android.util.Log;

public class OAuthGMailSender
{
    private static final String TAG = "OAuthGMailSender";

    private String mailhost = "smtp.gmail.com";
    private Integer mailport = 587;
    private String xoauthToken;

    static
    {
        Security.addProvider(new JSSEProvider());
    }

    public OAuthGMailSender(String xoauthToken)
    {
        this.xoauthToken = xoauthToken;

    }

    public synchronized void sendMail(String subject, String body, String sender, String recipient) throws Exception
    {
        Log.d(TAG, "Sending email...");
        SimpleSMTPHeader header = new SimpleSMTPHeader(sender, recipient, subject);

        AuthenticatingSMTPClient client = new AuthenticatingSMTPClient();
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        client.connect(mailhost, mailport);
        client.login();
        client.execTLS();
        client.auth(AuthenticatingSMTPClient.AUTH_METHOD.XOAUTH, xoauthToken, null);

        if (!SMTPReply.isPositiveCompletion(client.getReplyCode()))
        {
            Log.e(TAG, "SMTP Authentication failed: " + client.getReplyCode() + ":" + client.getReplyString());
            client.disconnect();
            throw new AndroidRuntimeException("SMTP Authentication failed.");
        }

        client.setSender(sender);
        client.addRecipient(recipient);

        Writer writer = client.sendMessageData();
        if (writer != null)
        {
            writer.write(header.toString());
            writer.write(body);
            writer.write("\n.\n");
            writer.close();
            client.completePendingCommand();
        }

        client.logout();

        client.disconnect();
    }

    @SuppressWarnings("serial")
    public static final class JSSEProvider extends Provider
    {
        public JSSEProvider()
        {
            super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
            AccessController.doPrivileged(new java.security.PrivilegedAction<Void>()
            {
                public Void run()
                {
                    put("SSLContext.TLS", "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                    put("Alg.Alias.SSLContext.TLSv1", "TLS");
                    put("KeyManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                    put("TrustManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                    return null;
                }
            });
        }
    }
}
