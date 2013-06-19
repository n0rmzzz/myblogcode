package com.tinywebgears.gmailoauth.util;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;

public interface OAuthHelper
{
    final String OAUTH_SCOPE_GMAIL = "https://mail.google.com/";
    final String OAUTH_SCOPE_EMAIL = "https://www.googleapis.com/auth/userinfo.email";
    final String OAUTH_SCOPE_PROFILE = "https://www.googleapis.com/auth/userinfo.profile";
    final String OAUTH_CALLBACK_URL = "http://localhost";
    final String OAUTH_APP_NAME = "RelayMe";

    final String URL_GET_EMAIL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
    final String URL_SMTP_AUTH = "https://mail.google.com/mail/b/%s/smtp/";

    String getAuthorizationUrl();

    Token getAccessToken(String verifier);

    String extractVerifier(String uri);

    void signRequest(Token accessToken, OAuthRequest request);

    String makeSecuredRequest(Token accessToken, String url);
}
