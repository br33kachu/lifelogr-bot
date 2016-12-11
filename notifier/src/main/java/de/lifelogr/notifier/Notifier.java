package de.lifelogr.notifier;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;
import de.lifelogr.recommendations.RecommendationsDrink;
import de.lifelogr.trackingobjects.TrackingObjectType;
import de.lifelogr.trackingobjects.TrackingObjects;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    Informant informant;
    TrackingObjects trackingObjects;
    RecommendationsDrink recommendationsDrink;
    TrackingObjectType trackingObjectType;

    private Notifier() {
        this.informant = Informant.getInstance();
        this.informant.register(this);
        this.trackingObjects = TrackingObjects.getInstance();
        this.recommendationsDrink = new RecommendationsDrink();
        this.trackingObjectType = null;
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
        if ((this.trackingObjectType == TrackingObjectType.KOFFEIN || this.trackingObjectType == TrackingObjectType.ALKOHOL) && this.recommendationsDrink.recommenationNeeded(user, trackingObjectType)) {
            Communicator.getInstance().sendMessage(user.getChatId().toString(), this.recommendationsDrink.recommend(trackingObjectType));
        } else if (this.trackingObjectType == TrackingObjectType.UNBEKANNT) {}
    }
}

