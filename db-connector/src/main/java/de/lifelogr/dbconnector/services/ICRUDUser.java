package de.lifelogr.dbconnector.services;

import com.mongodb.WriteResult;
import de.lifelogr.dbconnector.entity.User;

import java.util.List;

/**
 * Interface for CRUD-operations on Users.
 */
public interface ICRUDUser
{
    /**
     * Get all Users available.
     *
     * @return List of all Users.
     */
    List<User> getAllUsers();

    /**
     * Get a single User by Telegram-ID.
     *
     * @param id Telegram-ID
     * @return User
     */
    User getUserByTelegramId(Integer id);

    /**
     * Get a single User by Token,
     *
     * @param token Token
     * @return User
     */
    User getUserByToken(String token);

    /**
     * Persist a User-object.
     *
     * @param user User to be persisted.
     */
    void saveUser(User user);

    /**
     * Update a single field for a specific User.
     *
     * @param user User
     * @param field Field-name to be updated.
     * @param value New field-value.
     */
    void updateField(User user, String field, String value);

    /**
     * Delete a single User.
     *
     * @param user User to be deleted.
     * @return
     */
    WriteResult deleteUser(User user);
}
