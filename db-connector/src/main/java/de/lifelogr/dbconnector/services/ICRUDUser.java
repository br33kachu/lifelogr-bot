package de.lifelogr.dbconnector.services;

import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by micha on 10.12.2016.
 */
public interface ICRUDUser
{
    public List<User> getAllUsers();
    public User getUserByTelegramId(Integer id);
    public ObjectId getUserIdByToken(String token);
    public List<TrackingObject> getTrackingObjectByUserId(ObjectId id);
    public void saveUser(User user);
    public void updateField(User user, String field, String value);
}
