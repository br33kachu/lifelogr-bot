package de.lifelogr.notifier.trackingobjects;

import de.lifelogr.dbconnector.services.TrackingObjectType;

/**
 * To get the specific type of a tracking object.
 *
 * @author christin schlimbach
 */
public class TrackingObjectController {

    private static TrackingObjectController instance = null;
    private Drinks drinks;
    private Food food;
    private Sport sport;

    //constructor
    private TrackingObjectController() {

        this.drinks = new Drinks();
        this.food = new Food();
        this.sport = new Sport();
    }

    /**
     * Implements the singleton.
     *
     * @return Instance of the class
     */
    public static TrackingObjectController getInstance() {

        if (instance == null) {
            instance = new TrackingObjectController();
        }

        return instance;
    }

    /**
     * To get the specific type of a tracking object.
     *
     * @param trackingObject Tracking object
     * @return Specific tracking object type
     */
    public TrackingObjectType getType(String trackingObject) {

        if (this.drinks.getCaffeinList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.CAFFEIN;
        } else if (this.drinks.getAlcoholList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.ALCOHOL;
        } else if (this.drinks.getBeautyList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.BEAUTY;
        } else if (this.food.getSuperfoodList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.SUPERFOOD;
        } else if (this.food.getCandyList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.CANDY;
        } else if (this.food.getFastfoodList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.FASTFOOD;
        } else if (this.sport.getPowerList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.POWER;
        } else if (this.sport.getStaminaList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.STAMINA;
        } else if (this.sport.getRelaxList().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.RELAX;
        }

        return TrackingObjectType.UNBEKANNT;
    }
}
