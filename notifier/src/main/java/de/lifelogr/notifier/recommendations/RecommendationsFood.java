package de.lifelogr.notifier.recommendations;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Created by christin on 16.01.17.
 */
public class RecommendationsFood {

    private HashMap<TrackingObjectType, String[]> recommendationsFood = new HashMap<>();

    public RecommendationsFood() {
        this.recommendationsFood.put(TrackingObjectType.SUPERFOOD, new String[]
                {"Das gehört zu den Superfoods. Die sind super gesund und haben nur wenig Fett.",
                 "Superfoods sind einfach super!",
                 "Du lebst wirklich sehr gesund, Respekt!"});

        this.recommendationsFood.put(TrackingObjectType.CANDY, new String[]
                {"Meinst du nicht, du hast langsam genug Süßigkeiten?" + Emoji.SMILING_FACE_WITH_OPEN_MOUTH,
                 "Zu viele Süßigkeiten sind schlecht für die Figur."});

        this.recommendationsFood.put(TrackingObjectType.FASTFOOD, new String[]
                {"Das war aber jetzt schon ziemlich viel Fastfood in der letzten Zeit...",
                 "Iss doch lieber mal etwas Gesünderes"});
    }

    public HashMap<TrackingObjectType, String[]> getRecommendationsFood() {
        return recommendationsFood;
    }
}

