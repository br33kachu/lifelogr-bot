package de.lifelogr.webservice.controller;

import org.bson.types.ObjectId;

import static org.junit.Assert.*;

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
        ObjectId id = new ObjectId("5846e843135672de475dc4a8");
        String result = webController.getJSONDataSet(id);
        assertNotEquals(result, null);
        System.out.println(result);
    }

    @org.junit.Test
    public void getUserIdByToken_01() throws Exception {
        ObjectId id = webController.getUserIdByToken("112233aabbcc");
        ObjectId expectedId = new ObjectId("5846e843135672de475dc4a8");
        assertEquals(id.toString(), expectedId.toString());
    }

}