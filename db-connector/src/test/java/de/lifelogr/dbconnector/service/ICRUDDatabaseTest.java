package de.lifelogr.dbconnector.service;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by micha on 11.12.2016.
 */
public class ICRUDDatabaseTest {
    private final ICRUDUser icrudUser = new ICRUDUserImpl();
    private User testUser;
    private User dbUser;

    @AfterClass
    public static void deleteData() {

    }

    @Test
    public void insertUserTest01() {
        testUser = new User();
        testUser.setChatId(new Long(0000001));
        testUser.setTelegramId(00000001);
        icrudUser.saveUser(testUser);
        dbUser = icrudUser.getUserByTelegramId(123456789);
        assertEquals(testUser, dbUser);
    }


}
