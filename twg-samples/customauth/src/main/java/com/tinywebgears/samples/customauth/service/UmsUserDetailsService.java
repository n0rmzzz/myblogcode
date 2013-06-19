package com.tinywebgears.samples.customauth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UmsUserDetailsService implements UserDetailsService
{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

    private Logger logger = LoggerFactory.getLogger(UmsUserDetailsService.class);

    private ThreadLocal<User> currentUser = new ThreadLocal<User>();
    private ThreadLocal<String> currentPassword = new ThreadLocal<String>();

    private Map<String, String> usersToCookies = new HashMap<String, String>();
    private Map<String, String> cookiesToUsers = new HashMap<String, String>();
    private Map<String, Date> sessionValidity = new HashMap<String, Date>();

    public UmsUserDetailsService()
    {
        createSSOSession("user");
        createSSOSession("admin");
    }

    public String createSSOSession(String username)
    {
        logger.debug("Creating SSO session for " + username);
        String newCookieValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + username;
        String oldCookie = usersToCookies.get(username);
        removeSSOSession(oldCookie);
        cookiesToUsers.put(newCookieValue, username);
        Date now = new Date();
        Date sessionValidityTime = new Date(now.getTime() + 30 * 1000);
        sessionValidity.put(newCookieValue, sessionValidityTime);
        return newCookieValue;
    }

    public void removeSSOSession(String cookie)
    {
        if (cookie != null)
        {
            logger.debug("Removing SSO session: " + cookie);
            String username = cookiesToUsers.get(cookie);
            if (username != null)
                usersToCookies.remove(username);
            cookiesToUsers.remove(cookie);
            sessionValidity.remove(cookie);
        }
    }

    public UserDetails loadUserByCookie(String cookie) throws UsernameNotFoundException, DataAccessException
    {
        logger.debug("Loading user by cookie: " + cookie);
        Date sessionValidUntil = sessionValidity.get(cookie);
        Date now = new Date();
        logger.debug("Session valid until: " + sessionValidUntil + " now: " + now);
        if (sessionValidUntil == null || now.after(sessionValidUntil))
        {
            logger.debug("Session no longer valid.");
            cookie = null;
        }
        return loadUserByUsername(cookiesToUsers.get(cookie));
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        logger.debug("Loading user by name: " + username);
        if (username != null && username.equals("user"))
        {
            Vector<GrantedAuthority> userAuthorities = new Vector<GrantedAuthority>();
            userAuthorities.add(new GrantedAuthorityImpl(ROLE_USER));
            User user = new User("user", "Et6pb+wgWTVmq3VpLJlJWWgzrck=" /* SHA-1 encoded of "user" */, true, true,
                    true, true, userAuthorities.toArray(new GrantedAuthority[0]));
            currentUser.set(user);
            return user;
        }
        if (username != null && username.equals("admin"))
        {
            Vector<GrantedAuthority> userAuthorities = new Vector<GrantedAuthority>();
            userAuthorities.add(new GrantedAuthorityImpl(ROLE_USER));
            userAuthorities.add(new GrantedAuthorityImpl(ROLE_ADMINISTRATOR));
            User user = new User("admin", "0DPiKuNIrrVmD8IUCuw1hQxNqZc=" /* SHA-1 encoded of "admin" */, true, true,
                    true, true, userAuthorities.toArray(new GrantedAuthority[0]));
            currentUser.set(user);
            return user;
        }
        throw new UsernameNotFoundException("Username " + username + " not found!");
    }

    public User getCurrentUser()
    {
        return currentUser.get();
    }

    public void setCurrentUser(User user)
    {
        currentUser.set(user);
    }

    public String getPassword()
    {
        return currentPassword.get();
    }

    public void setPassword(String password)
    {
        currentPassword.set(password);
    }
}
