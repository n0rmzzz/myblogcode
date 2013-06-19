package com.tinywebgears.samples.customauth.controller;

import junit.framework.Assert;

import org.junit.Test;

import com.tinywebgears.samples.customauth.controller.ActionController;


public class ActionControllerTest
{
    @Test
    public void testController()
    {
        ActionController controller = new ActionController();
        Assert.assertEquals("fileview", controller.listFiles().getViewName());
    }
}
