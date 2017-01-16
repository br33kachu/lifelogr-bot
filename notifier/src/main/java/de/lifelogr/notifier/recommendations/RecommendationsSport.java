package de.lifelogr.notifier.recommendations;

import de.lifelogr.dbconnector.services.TrackingObjectType;

import java.util.HashMap;

/**
 * Created by christin on 16.01.17.
 */
public class RecommendationsSport {

    private HashMap<TrackingObjectType, String[]> recommendationsSport = new HashMap<>();

    public RecommendationsSport() {
        this.recommendationsSport.put(TrackingObjectType.POWER, new String[]
                {"Achtung! Zu viel Krafttraining ist auch nicht gut für deine Muskeln.",
                 "Du hast deine Muskeln schon sehr stark beansprucht. Gönn' dir lieber mal einen Tag Pause."});

        this.recommendationsSport.put(TrackingObjectType.STAMINA, new String[]
                {"Sehr gut! Ausdauertraining hält dich fit und gesund.",
                 "Super, das hält dein Herz fit und gesund!"});

        this.recommendationsSport.put(TrackingObjectType.RELAX, new String[]
                {"Jetzt bist du bestimmt sehr entspannt.",
                 "Das tut Körper und Geist gut.",
                 "Entspannung ist perfekt gegen den Alltagsstress!"});
    }

    public HashMap<TrackingObjectType, String[]> getRecommendationsSport() {
        return recommendationsSport;
    }
}
