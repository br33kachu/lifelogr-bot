package de.lifelogr.notifier.recommendations;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import de.lifelogr.dbconnector.services.TrackingObjectType;
import de.lifelogr.notifier.trackingobjects.TrackingObjects;

import java.util.Date;

/**
 * Created by Christin on 12.12.2016.
 */
public class RecommendationController {

    private RecommendationsDrink recommendationsDrink;
    private RecommendationsFood recommendationsFood;
    private RecommendationsSport recommendationsSport;
    private TrackingObjects trackingObject;

    public RecommendationController() {
        this.recommendationsDrink = new RecommendationsDrink();
        this.recommendationsFood = new RecommendationsFood();
        this.recommendationsSport = new RecommendationsSport();
        this.trackingObject = TrackingObjects.getInstance();
    }

    public boolean recommendationNeeded(User user, TrackingObjectType type) {
        Date currentDate = new Date();
        double counter = 0.0;
        if (user.getLastRecommendations() == null || user.getLastRecommendations().get(type) == null || (currentDate.getTime() - user.getLastRecommendations().get(type).getTime() >= 60 * 60 * 1000))
            for (TrackingObject tObject : user.getTrackingObjects()) {
                if (trackingObject.getType(tObject.getName()) == type) {
                    for (Track track : tObject.getTracks()) {
                        if (currentDate.getTime() - track.getDate().getTime() <= 2 * 60 * 60 * 1000) {
                            counter = counter + track.getCount();
                            if (counter > 3.0) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
    }

    public String[] recommend(User user, TrackingObjectType type) {
        user.addLastRecommendations(type, new Date());
        ICRUDUser icrudUser = new ICRUDUserImpl();
        icrudUser.saveUser(user);
        if (type == TrackingObjectType.CAFFEIN || type == TrackingObjectType.ALCOHOL || type == TrackingObjectType.BEAUTY) {
            return this.recommendationsDrink.getRecommendationsDrink().get(type);
        } else if (type == TrackingObjectType.SUPERFOOD || type == TrackingObjectType.CANDY || type == TrackingObjectType.FASTFOOD) {
            return this.recommendationsFood.getRecommendationsFood().get(type);
        } else if (type == TrackingObjectType.POWER || type == TrackingObjectType.STAMINA || type == TrackingObjectType.RELAX) {
            return this.recommendationsSport.getRecommendationsSport().get(type);
        }

        else return null;
    }
}
