package de.lifelogr.webservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by micha on 06.12.2016.
 */
public class WebControllerTest {
    private final WebController webController = new WebController();
    /*
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }
*/
    @org.junit.Test
    public void getJSONDataSet() throws Exception {
        int telegramId = 666999;
        String result = webController.getJSONDataSet(telegramId);
        assertNotEquals(result, null);
        System.out.println(result);
    }

    @org.junit.Test
    public void getUserIdByToken_01() throws Exception {
        int telegramId = webController.getTelegramIdByToken("112233aabbcc");
        int expectedId = 666999;
        assertEquals(telegramId, expectedId);
    }

}