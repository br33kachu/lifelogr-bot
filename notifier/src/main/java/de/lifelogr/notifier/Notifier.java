package de.lifelogr.notifier;

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


    private Notifier() {
        this.informant = Informant.getInstance();
        this.informant.register(this);
        this.trackingObjects = TrackingObjects.getInstance();
        this.recommendationsDrink = new RecommendationsDrink();
    }

    public static Notifier getInstance() {
        if (instance == null) {
            instance = new Notifier();
        }
        return instance;
    }

    @Override
    public void onInform(User user, TrackingObject trackingObject) {
        for (TrackingObject tObject:user.getTrackingObjects()) {
            if(this.trackingObjects.getType(tObject.getName()) == TrackingObjectType.KOFFEIN) {
                System.out.println(this.recommendationsDrink.recommend(TrackingObjectType.KOFFEIN));
            }
        }
    }
}

