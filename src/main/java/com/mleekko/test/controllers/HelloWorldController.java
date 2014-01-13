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
public class HelloWorldController {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String doHello(ModelMap model, @RequestParam(required = false) String page) {
        LOG.debug("Serving /hello with page: {}.", page);
        if (page == null) {
            page = String.valueOf(1);
        }

        model.put("pageNum", page);
        return "/hello";
    }

}
