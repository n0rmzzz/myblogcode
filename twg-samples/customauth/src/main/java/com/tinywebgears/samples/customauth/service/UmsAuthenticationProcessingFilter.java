package com.tinywebgears.samples.customauth.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UmsAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter
{
    private UmsUserDetailsService umsUserDetailsService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String cookieName;

    @Override
    protected String obtainPassword(HttpServletRequest request)
    {
        String password = super.obtainPassword(request);
        String passwordHash = hashPassword(password);
        umsUserDetailsService.setPassword(passwordHash);
        return password;
    }

    public String getCookieName()
    {
        return cookieName;
    }

    public void setCookieName(String cookieName)
    {
        this.cookieName = cookieName;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            Authentication authResult) throws ServletException, IOException
    {
        logger.debug("Unsuccessful authentication");
        User user = umsUserDetailsService.getCurrentUser();

        String password = umsUserDetailsService.getPassword();
        String currentCookie = getCookie(request);
        Cookie sso = createServerSSOSession(user, password, currentCookie);

        response.addCookie(sso);
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute(cookieName, currentCookie);
        super.successfulAuthentication(request, response, authResult);
    }

    private String getCookie(HttpServletRequest request)
    {
        // Check whether the browser already has a SSO cookie
        String currentCookie = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (int i = 0; i < cookies.length; i++)
                if (cookieName.equals(cookies[i].getName()))
                {
                    currentCookie = cookies[i].getValue();
                    break;
                }
        return currentCookie;
    }

    private Cookie createServerSSOSession(User user, String password, String currentCookie)
    {
        // Cookie value for authenticated session
        logger.debug("Creating a server SSO session: " + currentCookie);
        String newCookieValue = umsUserDetailsService.createSSOSession(user.getUsername());
        String cookieToUse = currentCookie;
        if (!currentCookie.equals(newCookieValue))
            cookieToUse = newCookieValue;
        logger.debug("Cookie to use: " + cookieToUse);
        Cookie cookie = new Cookie(cookieName, cookieToUse);
        cookie.setMaxAge(10 * 60);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws ServletException, IOException
    {
        logger.debug("Unsuccessful authentication");
        umsUserDetailsService.setCurrentUser(null);
        umsUserDetailsService.setPassword(null);
        super.unsuccessfulAuthentication(request, response, failed);
    }

    public static String hashPassword(String password)
    {
        String passwordHash = "";
        try
        {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(password.getBytes());
            Base64 encoder = new Base64();
            passwordHash = new String(encoder.encode(sha1.digest()));
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerFactory.getLogger(UmsAuthenticationProcessingFilter.class.getClass()).error(
                    "Failed to generate password hash: " + e.getMessage());
        }
        return passwordHash;
    }

    public UmsUserDetailsService getUmsUserDetailsService()
    {
        return umsUserDetailsService;
    }

    public void setUmsUserDetailsService(UmsUserDetailsService userDetailsService)
    {
        this.umsUserDetailsService = userDetailsService;
    }
}
