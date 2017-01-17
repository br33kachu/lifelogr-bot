package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;

/**
 * Abstract class for the Observer-Model.
 *
 * @author christin schlimbach
 */
public abstract class Observer {

    public abstract void onInform(User user, TrackingObject trackingObject);
}
