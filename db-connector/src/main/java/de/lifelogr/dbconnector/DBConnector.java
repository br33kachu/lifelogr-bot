package de.lifelogr.dbconnector;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import de.lifelogr.dbconnector.entity.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.List;

public class DBConnector
{
    private static DBConnector instance = null;
    private final static Object mutex = new Object();
    private final Morphia morphia = new Morphia();
    private Datastore datastore = null;

    private DBConnector()
    {
        MongoClientURI uri = new MongoClientURI("mongodb://syp:xjwyP0qu_YxL9L55p179tRLlBe3KNWMy@lifelogr.de/lifelog_bot");
        MongoClient mongoClient = new MongoClient(uri);
        morphia.mapPackage("de.lifelogr.dbconnector.entity");

        datastore = morphia.createDatastore(mongoClient, uri.getDatabase());
        datastore.ensureIndexes();
    }

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

    public void addUser(User user)
    {
        datastore.save(user);
    }

    public List<User> getAllUsers()
    {
        final Query<User> query = datastore.createQuery(User.class);

        return query.asList();
    }
}
