package com.tinywebgears.gaedownload.webapp;

import java.io.Serializable;
import java.util.Date;

public class UserSession implements Serializable
{
    private String username;
    private String email;
    private String screenName;
    private Boolean superuser;
    private Date loginTime;

    public String getUsername()
    {
        return username;
    }

    private void setUsername(String userid)
    {
        this.username = userid;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Boolean getSuperuser()
    {
        return superuser;
    }

    private void setSuperuser(Boolean superuser)
    {
        this.superuser = superuser;
    }

    public String getScreenName()
    {
        return screenName;
    }

    public void setScreenName(String screenName)
    {
        this.screenName = screenName;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    private void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    public void setSession(String username, String email, Boolean superuser, String nickName)
    {
        setUsername(username);
        setEmail(email);
        setSuperuser(superuser);
        setScreenName(nickName);
        setLoginTime(new Date());
    }

    public void clearSession()
    {
        setUsername(null);
        setEmail(null);
        setSuperuser(null);
        setScreenName(null);
        setLoginTime(null);
    }
}
