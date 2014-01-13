package com.mleekko.test.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mleekko
 */
@Controller
public class HomeController {
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(ModelMap model) {
        LOG.debug("Serving /root.");


        return "/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        LOG.debug("Serving /home.");


        return "/home";
    }
}
