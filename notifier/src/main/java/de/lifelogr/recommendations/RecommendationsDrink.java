package de.lifelogr.recommendations;

import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Created by Christin on 04.12.2016.
 */
public class RecommendationsDrink {

    private HashMap<TrackingObjectType, String[]> recommendations = new HashMap<>();

    public RecommendationsDrink() {
        this.recommendations.put(TrackingObjectType.KOFFEIN, new String[]
                {"Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.",
                "Trink doch lieber mal etwas Neutrales. Zu viel Koffein schadet deinem Magen."});
        this.recommendations.put(TrackingObjectType.ALKOHOL, new String[] {"Du hast schon ziemlich viel Alkohol getrunken. Trink lieber mal etwas langsamer."});
    }

    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}
