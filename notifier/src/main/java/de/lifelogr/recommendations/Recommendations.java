package de.lifelogr.recommendations;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import de.lifelogr.trackingobjects.TrackingObjectType;
import de.lifelogr.trackingobjects.TrackingObjects;

import java.util.Date;

/**
 * Created by Christin on 12.12.2016.
 */
public class Recommendations {

    private RecommendationsDrink recommendationsDrink;
    private TrackingObjects trackingObject;

    public Recommendations() {
        this.recommendationsDrink = new RecommendationsDrink();
        this.trackingObject = TrackingObjects.getInstance();
    }

    public boolean recommendationNeeded(User user, TrackingObjectType type, String category) {
        Date currentDate = new Date();
        double counter = 0.0;
        if (currentDate.getTime() - user.getLastRecommendation().getTime() >= 60 * 60 * 1000)
            for (TrackingObject tObject : user.getTrackingObjects()) {
                if (trackingObject.getType(tObject.getName()) == type) {
                    for (Track track : tObject.getTracks()) {
                        if (category.equals("drink")) {
                            if (currentDate.getTime() - track.getDate().getTime() <= 2 * 60 * 60 * 1000) {
                                counter = counter + track.getCount();
                                if (counter > 3.0) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return false;
    }

    public String recommend(User user, TrackingObjectType type) {
        user.setLastRecommendation(new Date());
        ICRUDUser icrudUser = new ICRUDUserImpl();
        icrudUser.saveUser(user);
        if(type == TrackingObjectType.KOFFEIN || type == TrackingObjectType.ALKOHOL) {
            return this.recommendationsDrink.getRecommendations().get(type);
        }
        else return "Error: Type not found";
    }
}
