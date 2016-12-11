package de.lifelogr.dbconnector.service;

import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

/**
 * Created by micha on 10.12.2016.
 */
public class ICRUDDatabase {
    private static final int STATUS_SUCCES = 1;
    private static final int STATUS_ERROR = -1;
    private static final int STATUS_ERROR_OBJECT_ALREADY_EXISTS = -2;
    private static final int STATUS_SUCCES_TRACK_INSERTED = 102;
    private static final int STATUS_ERROR_USER_NOT_FOUND = -3;

    DBConnector dbConnector;
    Datastore datastore;

    /**
     * Standardkonstruktor
     */
    public ICRUDDatabase() {
        dbConnector = DBConnector.getInstance();
        datastore = dbConnector.getDatastore();
    }

    /**
     * Mit einem gegegebenen ObjectId (identifikation eines Users), werden die TrackingObjekte eines Users zurück-
     * gegegeben.
     *
     * @return Eine Liste von TrackingObjekten
     */
    public List<TrackingObject> getTrackingObjectByUserId(ObjectId userId) {
        User user = datastore.get(User.class, userId);
        return user.getTrackingObjects();
    }

    /**
     * With a given Token, you can retreive the ObjectId
     *
     * @param token das übergebene Token-PIN
     * @return ObjectId des Users, falls ein User mit dem Token existiert - andernfalls null
     */
    public ObjectId getUserIdByToken(String token) {
        User user = datastore.createQuery(User.class)
                .field("token").equalIgnoreCase(token)
                .get();
        ObjectId objectId = user.getId();
        return objectId;
    }

    /**
     * Einfügen eines User-Objekts in die Datenbank
     *
     * @param user
     * @return
     */
    public Key<User> insertUser(User user) {
        if (user != null) {
            Key<User> userKey = datastore.save(user);
            return userKey;
        }
        return null;
    }

    /**
     * Hole einen User-Objekt aus der Datenkenbank
     *
     * @param telegramId die eindeutige TelegramID des Users
     * @return User-Objekt, falls ein User mit der telegramId existiert - andernfalls null
     */
    public User getUser(int telegramId) {
        User user = datastore.createQuery(User.class)
                .field("telegramId").equal(telegramId)
                .get();
        return user;
    }

    /**
     * Update eines TrackingObjekts:
     * Falls ein TrackingObjekt mit einem Track übergeben wird, wird ein Track hinzugefügt
     * Falls ein TrackingObjekt ohne Track übergeben wird, wird ein TrackingObjekt erstellt
     *
     * @param telegramId     die eindeutige Identifikation eines Users
     * @param trackingObject das hinzufügende TrackingObjekt
     * @return
     */
    public int updateTrackingObjectByTelegramId(int telegramId, TrackingObject trackingObject) {
        int status = 0;
        if (trackingObject != null) {
            // Erstelle einen UpdateQuery mit dem passenden User
            final Query<User> userQuery = datastore.createQuery(User.class)
                    .field("telegramId").equal(telegramId);
            User user = userQuery.get();
            // Wurde ein User gefunden?
            if (user != null) {
                List<TrackingObject> list = user.getTrackingObjects();
                // Gibt es eine TrackingObjekt Liste mit Track Objekten?
                if (list != null && !list.isEmpty()) {
                    for (TrackingObject tObject : list) {
                        // Existiert bereits ein TrackingObjekt mit dem gleichen Namen?
                        if (tObject.getName().equalsIgnoreCase(trackingObject.getName())) {
                            // Hat das übergebene TrackingObjekt einen Track?
                            if (trackingObject.getTracks() != null && !trackingObject.getTracks().isEmpty()) {
                                // -> Track hinzufügen!
                                tObject.getTracks().add(trackingObject.getTracks().get(0));
                                status = STATUS_SUCCES_TRACK_INSERTED;
                            } else {
                                return STATUS_ERROR_OBJECT_ALREADY_EXISTS;
                            }
                        }
                    }
                    // -> Füge einen neuen TrackingObjekt hinzu
                    if (status == 0) {
                        list.add(trackingObject);
                    }
                    UpdateOperations<User> updateOperations = datastore.createUpdateOperations(User.class)
                            .set("trackingObjects", list);
                    UpdateResults updateResults = datastore.update(userQuery, updateOperations);
                    return (status != 0) ? status : updateResults.getWriteResult().getN();
                }
            } else {
                return STATUS_ERROR_USER_NOT_FOUND;
            }
        }
        return STATUS_ERROR;
    }
}
