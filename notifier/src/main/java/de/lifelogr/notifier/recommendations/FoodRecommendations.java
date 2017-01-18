package de.lifelogr.notifier.recommendations;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Contains the hashmap for the food recommendations.
 *
 * @author christin schlimbach
 */
public class FoodRecommendations {

    private HashMap<TrackingObjectType, String[]> recommendations = new HashMap<>();

    /**
     * Constructor
     *
     * Puts the recommendations for the different food types into the hashmap.
     */
    public FoodRecommendations() {

        //recommendations for type candy
        this.recommendations.put(TrackingObjectType.CANDY, new String[]
                {"Meinst du nicht, du hast langsam genug S\u00fc\u00dfigkeiten?" + Emoji.SMILING_FACE_WITH_OPEN_MOUTH,
                 "Zu viele S\u00fc\u00dfigkeiten sind schlecht f\u00fcr die Figur."});

        //recommendations for type fastfood
        this.recommendations.put(TrackingObjectType.FASTFOOD, new String[]
                {"Das war aber jetzt schon ziemlich viel Fastfood in der letzten Zeit...",
                 "Iss doch lieber mal etwas Ges\u00fcnderes"});

        //recommendations for type superfood
        this.recommendations.put(TrackingObjectType.SUPERFOOD, new String[]
                {"Das geh\u00f6rt zu den Superfoods. Die sind super gesund und haben nur wenig Fett.",
                 "Superfoods sind einfach super!",
                 "Du lebst wirklich sehr gesund, Respekt!"});
    }

    //getter
    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}

