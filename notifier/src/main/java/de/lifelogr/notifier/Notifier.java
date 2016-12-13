package de.lifelogr.notifier;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;
import de.lifelogr.recommendations.Recommendations;
import de.lifelogr.trackingobjects.TrackingObjectType;
import de.lifelogr.trackingobjects.TrackingObjects;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    Informant informant;
    TrackingObjects trackingObjects;
    Recommendations recommendations;
    TrackingObjectType trackingObjectType;
    Communicator communicator;

    private Notifier() {
        this.informant = Informant.getInstance();
        this.informant.register(this);
        this.trackingObjects = TrackingObjects.getInstance();
        this.recommendations = new Recommendations();
        this.trackingObjectType = null;
        this.communicator = Communicator.getInstance();
    }

    public static Notifier getInstance() {
        if (instance == null) {
            instance = new Notifier();
        }
        return instance;
    }

    @Override
    public void onInform(User user, TrackingObject trackingObject) {
        this.trackingObjectType = trackingObjects.getType(trackingObject.getName());
        if ((this.trackingObjectType == TrackingObjectType.KOFFEIN || this.trackingObjectType == TrackingObjectType.ALKOHOL) && this.recommendations.recommendationNeeded(user, trackingObjectType, "drink")) {
            this.communicator.sendMessage(user.getChatId().toString(), this.recommendations.recommend(user, trackingObjectType));
        } else if (this.trackingObjectType == TrackingObjectType.UNBEKANNT) {}
    }
}

