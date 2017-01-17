package de.lifelogr.dbconnector.service;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by micha on 11.12.2016.
 */
public class ICRUDDatabaseTest {
    private final ICRUDUser icrudUser = new ICRUDUserImpl();
    private User testUser;
    private User dbUser;

    @Before
    public void createUser() {
        testUser = new User();
        testUser.setTelegramId(123456789);
        testUser.setCreatedAt(new Date());
        testUser.setUsername("TestUser");
        testUser.setToken("66665");
        icrudUser.saveUser(testUser);
    }

    @After
    public void deleteData() {
        icrudUser.deleteUser(testUser);
    }

    /**
     * Wenn ein User mit der TelegramID existiert,
     * der User in der Datenbank vorhanden ist,
     * hole den User mit der TelegramID
     */
    @Test
    public void getUserByTelegramId_00() {
        dbUser = icrudUser.getUserByTelegramId(123456789);
        assertEquals(testUser.getTelegramId(), dbUser.getTelegramId());
        assertEquals(testUser.getToken(), dbUser.getToken());
        assertEquals(testUser.getCreatedAt(), dbUser.getCreatedAt());
    }

    /**
     * Wenn ein User mit der TelegramID nicht existiert,
     * und/oder der User in der Datenbank nicht vorhanden ist,
     * hole den User mit der TelegramID
     */
    @Test
    public void getUserByTelegramId_01() {
        dbUser = icrudUser.getUserByTelegramId(987654321);
        assertNull(dbUser);
    }

    /**
     * Wenn ein User mit dem Token existiert,
     * der User in der Datenbank vorhanden ist,
     * hole den User mit dem Token
     */
    @Test
    public void getUserByToken_00() {
        dbUser = icrudUser.getUserByToken("66665");
        assertEquals(testUser.getTelegramId(), dbUser.getTelegramId());
        assertEquals(testUser.getToken(), dbUser.getToken());
        assertEquals(testUser.getCreatedAt(), dbUser.getCreatedAt());
    }

    /**
     * Wenn ein User mit dem Token nicht existiert,
     * und/oder der User in der Datenbank nicht vorhanden ist,
     * hole den User mit dem Token
     */
    @Test
    public void getUserByToken_01() {
        dbUser = icrudUser.getUserByToken("66666");
        assertNull(dbUser);
    }

    /**
     * Wenn ein User mit dem Token nicht existiert,
     * und/oder der User in der Datenbank nicht vorhanden ist,
     * hole den User mit einem Token, laenger als 5 Zeichen
     */
    @Test
    public void getUserByToken_02() {
        dbUser = icrudUser.getUserByToken("666655");
        assertNull(dbUser);
    }

    /**
     * Wenn ein User mit dem Token nicht existiert,
     * und/oder der User in der Datenbank nicht vorhanden ist,
     * hole den User mit einem leeren Token
     */
    @Test
    public void getUserByToken_03() {
        dbUser = icrudUser.getUserByToken("");
        assertNull(dbUser);
    }

}
