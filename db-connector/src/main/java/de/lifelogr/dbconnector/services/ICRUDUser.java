package de.lifelogr.dbconnector.services;

import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import org.bson.types.ObjectId;

import java.util.List;

public interface ICRUDUser
{
    List<User> getAllUsers();
    User getUserByTelegramId(Integer id);
    User getUserByToken(String token);
    List<TrackingObject> getTrackingObjectByUserId(ObjectId id);
    void saveUser(User user);
    void updateField(User user, String field, String value);
}
