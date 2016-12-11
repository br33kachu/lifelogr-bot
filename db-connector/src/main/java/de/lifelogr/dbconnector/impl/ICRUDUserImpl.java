package de.lifelogr.dbconnector.impl;

import com.mongodb.operation.UpdateOperation;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * @author marco
 */
public class ICRUDUserImpl implements ICRUDUser
{
    private Datastore ds;

    public ICRUDUserImpl()
    {
        this.ds = DBConnector.getInstance().getDatastore();
    }

    @Override
    public List<User> getAllUsers()
    {
        return ds.createQuery(User.class).asList();
    }

    @Override
    public User getUserByTelegramId(Integer id)
    {
        DBConnector dbc = DBConnector.getInstance();

        return dbc.getDatastore().createQuery(User.class).field("telegramId").equal(id).get();
    }

    @Override
    public ObjectId getUserIdByToken(String token)
    {
        return null;
    }

    @Override
    public List<TrackingObject> getTrackingObjectByUserId(ObjectId id)
    {
        return null;
    }

    @Override
    public void saveUser(User user)
    {
        this.ds.save(user);
    }

    @Override
    public void updateField(User user, String field, String value)
    {
        Query query = this.ds.createQuery(User.class).field("_id").equal(user.getId());
        UpdateOperations<User> ops = this.ds.createUpdateOperations(User.class).set(field, value);

        this.ds.update(query, ops);
    }
}
