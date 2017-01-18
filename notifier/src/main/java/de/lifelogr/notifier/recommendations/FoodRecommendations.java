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
                {"Meinst du nicht, du hast langsam genug S\u00fc\u00dfigkeiten ?" + Emoji.SMILING_FACE_WITH_OPEN_MOUTH,
                 "Zu viele S\u00fc\u00dfigkeiten sind schlecht f\u00fcr die Figur " + Emoji.CONFOUNDED_FACE});

        //recommendations for type fastfood
        this.recommendations.put(TrackingObjectType.FASTFOOD, new String[]
                {"Das war aber jetzt schon ziemlich viel Fastfood in der letzten Zeit..." + Emoji.CONFOUNDED_FACE,
                 "Iss doch lieber mal etwas Ges\u00fcnderes " + Emoji.CONFOUNDED_FACE});

        //recommendations for type superfood
        this.recommendations.put(TrackingObjectType.SUPERFOOD, new String[]
                {"Das geh\u00f6rt zu den Superfoods. Die sind super gesund und haben nur wenig Fett." + Emoji.THUMBS_UP_SIGN,
                 "Superfoods sind einfach super! " + Emoji.THUMBS_UP_SIGN,
                 "Du lebst wirklich sehr gesund, Respekt! " + Emoji.THUMBS_UP_SIGN});
    }

    //getter
    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}

