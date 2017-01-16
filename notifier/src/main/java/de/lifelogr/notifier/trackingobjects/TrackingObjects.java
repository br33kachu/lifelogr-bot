package de.lifelogr.notifier.trackingobjects;

import de.lifelogr.dbconnector.services.TrackingObjectType;

/**
 * Created by christin on 06.12.16.
 */
public class TrackingObjects {

    private static TrackingObjects instance = null;
    private Drinks drinks;
    private Food food;
    private Sport sport;

    private TrackingObjects() {
        this.drinks = new Drinks();
        this.food = new Food();
        this.sport = new Sport();
    }

    public static TrackingObjects getInstance() {
        if (instance == null) {
            instance = new TrackingObjects();
        }
        return instance;
    }

    public TrackingObjectType getType(String trackingObject) {
        if (this.drinks.getDrinksCaffein().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.CAFFEIN;
        } else if (this.drinks.getDrinksAlcohol().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.ALCOHOL;
        } else if (this.drinks.getDrinksBeauty().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.BEAUTY;
        } else if (this.food.getFoodSuperfood().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.SUPERFOOD;
        } else if (this.food.getFoodCandy().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.CANDY;
        } else if (this.food.getFoodFastfood().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.FASTFOOD;
        } else if (this.sport.getSportPower().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.POWER;
        } else if (this.sport.getSportStamina().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.STAMINA;
        } else if (this.sport.getSportRelax().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.RELAX;
        }

        return TrackingObjectType.UNBEKANNT;
    }
}
