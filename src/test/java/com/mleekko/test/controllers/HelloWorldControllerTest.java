package com.mleekko.test.controllers;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.springframework.ui.ModelMap;

/**
 * @author Mleekko
 */
public class HelloWorldControllerTest {

    @Test
    public void testSomething() {
        ModelMap model = new ModelMap();
        HelloWorldController controller = new HelloWorldController();

        String view = controller.doHello(model, "8");

        assertEquals("/hello", view);
        assertEquals("8", model.get("pageNum"));
    }

}
