package de.lifelogr.dbconnector.service;

import de.lifelogr.dbconnector.entity.User;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by micha on 11.12.2016.
 */
public class ICRUDDatabaseTest {
    private final ICRUDDatabase icrudDatabase = new ICRUDDatabase();
    private User testUser;
    private User dbUser;

    @AfterClass
    public static void deleteData() {

    }

    @Test
    public void insertUserTest01() {
        testUser = new User();
        testUser.setChatId(00000001);
        testUser.setTelegramId(00000001);
        icrudDatabase.insertUser(testUser);
        dbUser = icrudDatabase.getUser(123456789);
        assertEquals(testUser, dbUser);
    }


}
