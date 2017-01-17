package de.lifelogr.notifier;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;
import de.lifelogr.notifier.recommendations.RecommendationController;
import de.lifelogr.dbconnector.services.TrackingObjectType;
import de.lifelogr.notifier.trackingobjects.TrackingObjectController;

import java.util.Random;

/**
 * To send a fitting recommendation to the user.
 *
 * @author christin schlimbach
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    private Informant informant;
    private TrackingObjectController trackingObjectController;
    private RecommendationController recommendationController;
    private TrackingObjectType trackingObjectType;
    private Communicator communicator;

    //constructor
    private Notifier() {

        this.informant = Informant.getInstance();
        this.informant.register(this);
        this.trackingObjectController = TrackingObjectController.getInstance();
        this.recommendationController = new RecommendationController();
        this.trackingObjectType = null;
        this.communicator = Communicator.getInstance();
    }

    /**
     * Implements the singelton
     *
     * @return Instance of the class
     */
    public static Notifier getInstance() {

        if (instance == null) {
            instance = new Notifier();
        }

        return instance;
    }

    /**
     * Sends the fitting recommendation to the user.
     *
     * Using the type of the tracking object to get the fitting recommendations.
     * Then sends a random recommendation of the fitting recommendations to the user.
     * @param user Current user
     * @param trackingObject Updates tracking object
     */
    @Override
    public void onInform(User user, TrackingObject trackingObject) {

        this.trackingObjectType = trackingObjectController.getType(trackingObject.getName());

        if (this.trackingObjectType != TrackingObjectType.UNBEKANNT && this.recommendationController.needRecommendation(user, trackingObjectType)) {
            String[] result = this.recommendationController.getRecommendationsForType(user, trackingObjectType);
            int random = new Random().nextInt(result.length);
            //send message
            this.communicator.sendMessage(user.getChatId().toString(), result[random]);
        }
    }
}

