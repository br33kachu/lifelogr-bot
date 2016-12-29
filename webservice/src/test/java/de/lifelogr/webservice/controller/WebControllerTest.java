package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by micha on 06.12.2016.
 */
public class WebControllerTest {
    private final WebController webController = new WebController();

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    /*
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
*/

    /**
     * Hole JSON-DataSet,
     * wenn Tracks in der Datenbank vorhanden sind,
     * kein Datum eingegeben wurde
     */
    @Test
    public void testGetJSONDataSet_00() {
        int telegramId = 292994467;
        User user = webController.getUserByTelegramId(telegramId);
        String dataSet = webController.getJSONDataSet(telegramId, null, null);
        assertNotNull(dataSet);
        assertNotEquals(3, dataSet.length());
    }

    /**
     * Hole JSON-DataSet,
     * wenn keine Tracks in der Datenbank vorhanden sind
     * kein Datum eingegeben wurde
     */
    @Test
    public void testGetJSONDataSet_01() {
        int telegramId = 292994468;
        User user = webController.getUserByTelegramId(telegramId);
        String dataSet = webController.getJSONDataSet(telegramId, null, null);
        assertEquals("[]", dataSet);
    }

    /**
     * Hole JSON-DataSet
     * wenn kein User mit der telegramId existiert
     */
    @Test
    public void testGetJSONDataSet_02() {
        int telegramId = 292994469;
        User user = webController.getUserByTelegramId(telegramId);
        String dataSet = webController.getJSONDataSet(telegramId, null, null);
        assertEquals("[]", dataSet);
    }


}