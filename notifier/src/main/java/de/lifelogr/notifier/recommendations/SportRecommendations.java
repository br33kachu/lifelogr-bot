package de.lifelogr.notifier.recommendations;

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
                {"Achtung! Zu viel Krafttraining ist auch nicht gut f\u00fcr deine Muskeln.",
                 "Du hast deine Muskeln schon sehr stark beansprucht. G\u00f6nn' dir lieber mal einen Tag Pause."});

        //recommendations for type relax
        this.recommendations.put(TrackingObjectType.RELAX, new String[]
                {"Jetzt bist du bestimmt sehr entspannt.",
                 "Das tut K\u00f6rper und Geist gut.",
                 "Entspannung ist perfekt gegen den Alltagsstress!"});

        //recommendations for type stamina
        this.recommendations.put(TrackingObjectType.STAMINA, new String[]
                {"Sehr gut! Ausdauertraining h\u00e4lt dich fit und gesund.",
                 "Super, das h\u00e4lt dein Herz fit und gesund!"});
    }

    //getter
    public HashMap<TrackingObjectType, String[]> getRecommendations() {
        return recommendations;
    }
}
