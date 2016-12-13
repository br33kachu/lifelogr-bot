package de.lifelogr.notifier;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;
import de.lifelogr.notifier.recommendations.RecommendationController;
import de.lifelogr.dbconnector.services.TrackingObjectType;
import de.lifelogr.notifier.trackingobjects.TrackingObjects;

import java.util.Random;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    private Informant informant;
    private TrackingObjects trackingObjects;
    private RecommendationController recommendationController;
    private TrackingObjectType trackingObjectType;
    private Communicator communicator;

    private Notifier() {
        this.informant = Informant.getInstance();
        this.informant.register(this);
        this.trackingObjects = TrackingObjects.getInstance();
        this.recommendationController = new RecommendationController();
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
        if ((this.trackingObjectType == TrackingObjectType.CAFFEIN || this.trackingObjectType == TrackingObjectType.ALCOHOL || this.trackingObjectType == TrackingObjectType.BEAUTY) && this.recommendationController.recommendationNeeded(user, trackingObjectType, "drink")) {
            String[] result = this.recommendationController.recommend(user, trackingObjectType);
            int random = new Random().nextInt(result.length);
            this.communicator.sendMessage(user.getChatId().toString(), result[random]);
        } else if (this.trackingObjectType == TrackingObjectType.UNBEKANNT) {}
    }
}

