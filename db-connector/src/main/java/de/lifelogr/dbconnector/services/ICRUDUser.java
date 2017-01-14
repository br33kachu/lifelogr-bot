package de.lifelogr.dbconnector.services;

import com.mongodb.WriteResult;
import de.lifelogr.dbconnector.entity.User;

import java.util.List;

public interface ICRUDUser
{
    List<User> getAllUsers();
    User getUserByTelegramId(Integer id);
    User getUserByToken(String token);
    void saveUser(User user);
    void updateField(User user, String field, String value);

    WriteResult deleteUser(User user);
}
