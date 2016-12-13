package de.lifelogr.notifier.trackingobjects;

import de.lifelogr.dbconnector.services.TrackingObjectType;

/**
 * Created by christin on 06.12.16.
 */
public class TrackingObjects {

    private static TrackingObjects instance = null;
    private Drinks drinks = new Drinks();

    private TrackingObjects() {}

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
        }
        return TrackingObjectType.UNBEKANNT;
    }
}
