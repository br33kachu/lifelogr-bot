package de.lifelogr.recommendations;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.trackingobjects.TrackingObjectType;
import de.lifelogr.trackingobjects.TrackingObjects;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Christin on 04.12.2016.
 */
public class RecommendationsDrink {

    private HashMap<TrackingObjectType, String> recommendations = new HashMap<>();

    public RecommendationsDrink() {
        this.recommendations.put(TrackingObjectType.KOFFEIN, "Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.");
        this.recommendations.put(TrackingObjectType.ALKOHOL, "Du hast schon ziemlich viel Alkohol getrunken. Trink lieber mal etwas langsamer.");
    }

    public HashMap<TrackingObjectType, String> getRecommendations() {
        return recommendations;
    }
}
