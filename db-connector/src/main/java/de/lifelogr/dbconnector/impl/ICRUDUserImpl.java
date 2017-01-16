package de.lifelogr.dbconnector.impl;

import com.mongodb.WriteResult;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Implementation for the ICRUDUser Interface.
 *
 * @author Marco Kretz, Christin Schlimbach, Michael Pham
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
        return this.ds.createQuery(User.class).field("telegramId").equal(id).get();
    }

    @Override
    public User getUserByToken(String token)
    {
        return this.ds.createQuery(User.class).field("token").equal(token).get();
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

    @Override
    public WriteResult deleteUser(User user)
    {
        return null;
    }
}
