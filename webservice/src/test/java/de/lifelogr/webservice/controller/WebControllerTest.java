package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.junit.Test;

import java.util.Date;

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
        System.out.println(dataSet);
        assertNotNull(dataSet);
        assertNotEquals("[]", dataSet);
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
        System.out.println(dataSet);
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
        System.out.println(dataSet);
        assertEquals("[]", dataSet);
    }


    /**
     * Hole JSON-DataSet,
     * wenn Tracks in der Datenbank vorhanden sind,
     * ein Datum eingegeben wurde
     */
    @Test
    public void testGetJSONDataSet_03() {
        int telegramId = 292994467;
        User user = webController.getUserByTelegramId(telegramId);
        String dataSet = webController.getJSONDataSet(telegramId, null, null);
        System.out.println(dataSet);
        assertNotNull(dataSet);
        assertNotEquals(2, dataSet.length());
    }

    /**
     * Teste Token,
     * User mit dem Token existiert,
     * Verfallsdatum des Tokens wird nicht überschritten
     */
    @Test
    public void testGetUserByTelegramId_00() {
        int telegramId = 292994467;
        User user = webController.getUserByTelegramId(telegramId);
        user.setTokenExpirationDate(new Date(new Date().getTime() + 90000));
        ICRUDUser icrudUser = new ICRUDUserImpl();
        icrudUser.saveUser(user);
        int expectedId = user.getTelegramId();
        int userId = webController.getTelegramIdByToken(user.getToken());
        assertEquals(expectedId, userId);
    }

    /**
     * Teste Token,
     * User mit dem Token existiert,
     * Verfallsdatum des Tokens ist überschritten
     */
    @Test
    public void testGetUserByTelegramId_01() {
        int telegramId = 292994467;
        User user = webController.getUserByTelegramId(telegramId);
        user.setTokenExpirationDate(new Date(new Date().getTime() - 90000));
        ICRUDUser icrudUser = new ICRUDUserImpl();
        icrudUser.saveUser(user);
        int userId = webController.getTelegramIdByToken(user.getToken());
        assertEquals(-1, userId);
    }

    /**
     * Teste Token,
     * User mit dem Token existiert nicht
     */
    @Test
    public void testGetUserByTelegramId_02() {
        int userId = webController.getTelegramIdByToken("00000");
        assertEquals(0, userId);
    }
}