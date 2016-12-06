package de.lifelogr.dbconnector;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.Date;
import java.util.List;

public class DBConnector
{
    private static DBConnector instance = null;
    private final static Object mutex = new Object();
    private final Morphia morphia = new Morphia();
    private Datastore datastore = null;

    /**
     * Private constructor, so none can create an instance of DBConnector.
     */
    private DBConnector()
    {
        // Build MongoDB auth URI
        MongoClientURI uri = new MongoClientURI("mongodb://syp:xjwyP0qu_YxL9L55p179tRLlBe3KNWMy@lifelogr.de/lifelog_bot");
        MongoClient mongoClient = new MongoClient(uri);

        // Map entities to MongoDB
        morphia.mapPackage("de.lifelogr.dbconnector.entity");

        // Connect to MongoDB
        datastore = morphia.createDatastore(mongoClient, uri.getDatabase());
        datastore.ensureIndexes();
    }

    /**
     * Get the one and only instance of DBConnector. Thread-safe.
     *
     * @return DBConnector instance.
     */
    public static DBConnector getInstance()
    {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new DBConnector();
                }
            }
        }

        return instance;
    }

    public DBCollection getUsersCollection()
    {
        return this.datastore.getCollection(User.class);
    }

    /**
     * Save a single User to the database.
     *
     * @param user User to be saved.
     */
    public void saveUser(User user)
    {
        datastore.save(user);
    }

    /**
     * Retrieve a List of all Users in the database.
     *
     * @return List of Users.
     */
    public List<User> getAllUsers()
    {
        final Query<User> query = datastore.createQuery(User.class);

        return query.asList();
    }


    /**
     * Retrieve a JSON-String containing a List of all Tracking-Objects
     *
     * @return List of TrackingObjects if a User with matching token exists
     */
    public List<TrackingObject> getTrackingObjectByUserId(ObjectId userId)
    {
        User user = datastore.get(User.class, userId);
        Date date = new Date();
        if(date.before(user.getTokenExpirationDate())) {
            return user.getTrackingObjects();
        }
        return null;
    }

    public ObjectId getUserIdByToken(String token)
    {
        List<User> userList = datastore.createQuery(User.class)
                .field("token").equal(token)
                .asList();
        if(userList.size() == 1) {
            ObjectId objectId = userList.get(0).getId();
            return objectId;
        }
        return null;
    }
}
