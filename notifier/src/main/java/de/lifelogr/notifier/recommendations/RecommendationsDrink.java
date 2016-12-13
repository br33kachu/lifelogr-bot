package de.lifelogr.notifier.recommendations;

import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Created by Christin on 04.12.2016.
 */
public class RecommendationsDrink {

    private HashMap<TrackingObjectType, String[]> recommendations = new HashMap<>();

    public RecommendationsDrink() {
        this.recommendations.put(TrackingObjectType.CAFFEIN, new String[]
                {"Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.",
                "Trink doch lieber mal etwas Neutrales. Zu viel Koffein schadet deinem Magen."});
        this.recommendations.put(TrackingObjectType.ALCOHOL, new String[] {"Du hast schon ziemlich viel Alkohol getrunken. Trink lieber mal etwas langsamer."});
        this.recommendations.put(TrackingObjectType.BEAUTY, new String[] {"Wow, das ist ein echter Schönmacher!",
                "Das wirkt echt Wunder für Haut und Haare!",
                "Super! Das macht Haut und Haare gesund und schön."});
    }

    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}
