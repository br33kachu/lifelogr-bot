package de.lifelogr.recommendations;

import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.trackingobjects.TrackingObjectType;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Christin on 04.12.2016.
 */
public class RecommendationsDrink {

    private HashMap<TrackingObjectType, String> recommendations = new HashMap<>();

    public RecommendationsDrink() {
        this.recommendations.put(TrackingObjectType.KOFFEIN, "Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.");
    }

    public boolean recommenationNeeded() {
        return true;
    }

    public String recommend(TrackingObjectType type) {
        if (type.equals(TrackingObjectType.KOFFEIN)) {
            return this.recommendations.get(TrackingObjectType.KOFFEIN);
        }
        return "fehler";
    }
}
