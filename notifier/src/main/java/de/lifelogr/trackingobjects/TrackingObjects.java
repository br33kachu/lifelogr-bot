package de.lifelogr.trackingobjects;

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
            return TrackingObjectType.KOFFEIN;
        } else if (this.drinks.getDrinksAlcohol().contains(trackingObject.toLowerCase())) {
            return TrackingObjectType.ALKOHOL;
        }
        return TrackingObjectType.UNBEKANNT;
    }
}
