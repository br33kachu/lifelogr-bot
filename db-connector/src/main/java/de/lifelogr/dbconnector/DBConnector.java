package de.lifelogr.dbconnector;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import de.lifelogr.dbconnector.entity.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class DBConnector
{
    private final static Object mutex = new Object();
    private static DBConnector instance = null;
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

    public Datastore getDatastore() {
        return datastore;
    }
}
