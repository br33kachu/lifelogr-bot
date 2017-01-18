package de.lifelogr.notifier.recommendations;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Contains the hashmap for the sport recommendations.
 *
 * @author christin schlimbach
 */
public class SportRecommendations {

    private HashMap<TrackingObjectType, String[]> recommendations = new HashMap<>();

    /**
     * Constructor
     *
     * Puts the recommendations for the different sport types into the hashmap.
     */
    public SportRecommendations() {

        //recommendations for type power
        this.recommendations.put(TrackingObjectType.POWER, new String[]
                {"Achtung! Zu viel Krafttraining ist auch nicht gut f\u00fcr deine Muskeln " + Emoji.CONFOUNDED_FACE,
                 "Du hast deine Muskeln schon sehr stark beansprucht. G\u00f6nn' dir lieber mal einen Tag Pause. " + Emoji.RELIEVED_FACE});

        //recommendations for type relax
        this.recommendations.put(TrackingObjectType.RELAX, new String[]
                {"Jetzt bist du bestimmt sehr entspannt " + Emoji.RELIEVED_FACE,
                 "Das tut K\u00f6rper und Geist gut " + Emoji.RELIEVED_FACE,
                 "Perfekt gegen den Alltagsstress " + Emoji.RELIEVED_FACE});

        //recommendations for type stamina
        this.recommendations.put(TrackingObjectType.STAMINA, new String[]
                {"Sehr gut! Ausdauertraining h\u00e4lt dich fit und gesund " + Emoji.THUMBS_UP_SIGN,
                 "Super, das h\u00e4lt dein Herz fit und gesund! " + Emoji.THUMBS_UP_SIGN});
    }

    //getter
    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}
