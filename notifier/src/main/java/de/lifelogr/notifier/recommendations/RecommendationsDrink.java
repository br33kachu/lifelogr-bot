package de.lifelogr.notifier.recommendations;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Created by Christin on 04.12.2016.
 */
public class RecommendationsDrink {

    private HashMap<TrackingObjectType, String[]> recommendationsDrink = new HashMap<>();

    public RecommendationsDrink() {
        this.recommendationsDrink.put(TrackingObjectType.CAFFEIN, new String[]
                {"Du hast heute schon sehr viel Koffein zu dir genommen! Trink doch lieber mal ein Glas Wasser, das schont deinen Magen.",
                "Trink doch lieber mal etwas Neutrales. Zu viel Koffein schadet deinem Magen."});

        this.recommendationsDrink.put(TrackingObjectType.ALCOHOL, new String[]
                {"Bist du nicht langsam betrunken? " + Emoji.CLINKING_BEER_MUGS + "\nPass lieber ein bisschen auf, sonst wirst du es morgen bereuen!"});

        this.recommendationsDrink.put(TrackingObjectType.BEAUTY, new String[]
                {"Wow, das ist ein echter Schönmacher!",
                "Das wirkt wirklich Wunder für Haut und Haare!",
                "Super! Das macht Haut und Haare gesund und schön."});
    }

    public HashMap<TrackingObjectType, String[]> getRecommendationsDrink() {
        return recommendationsDrink;
    }
}
