package com.tinywebgears.samples.customauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActionController
{
    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);

    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    public ModelAndView listFiles()
    {
        logger.info("ActionController.doAction");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("fileview");
        mav.addObject("message", "No Files Yet!");
        return mav;
    }
}
