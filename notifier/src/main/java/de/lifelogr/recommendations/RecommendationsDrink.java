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
    private TrackingObjects trackingObject;

    public RecommendationsDrink() {
        this.trackingObject = TrackingObjects.getInstance();
        this.recommendations.put(TrackingObjectType.KOFFEIN, "Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.");
        this.recommendations.put(TrackingObjectType.ALKOHOL, "Du hast schon ziemlich viel Alkohol getrunken. Trink lieber mal etwas langsamer.");
    }

    public boolean recommenationNeeded(User user, TrackingObjectType type) {
        for (TrackingObject tObject : user.getTrackingObjects()) {
            if (trackingObject.getType(tObject.getName()) == type) {
                for (Track track : tObject.getTracks()) {

                }
            }
        }
        return true;
    }

    public String recommend(TrackingObjectType type) {
        if (type.equals(TrackingObjectType.KOFFEIN)) {
            return this.recommendations.get(TrackingObjectType.KOFFEIN);
        } else if (type.equals(TrackingObjectType.ALKOHOL)) {
            return this.recommendations.get(TrackingObjectType.ALKOHOL);
        }
        else return "Error: Type not found";
    }
}
