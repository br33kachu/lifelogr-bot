package de.lifelogr.notifier.recommendations;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import de.lifelogr.dbconnector.services.TrackingObjectType;
import de.lifelogr.notifier.trackingobjects.TrackingObjectController;

import java.util.Date;

/**
 * Checks if recommendations are needed for a specific user and to get the recommendations for a specific type.
 *
 * @author christin schlimbach
 */
public class RecommendationController {

    private DrinkRecommendations drinkRecommendations;
    private FoodRecommendations foodRecommendations;
    private SportRecommendations sportRecommendations;
    private TrackingObjectController trackingObjectController;

    //constructor
    public RecommendationController() {

        this.drinkRecommendations = new DrinkRecommendations();
        this.foodRecommendations = new FoodRecommendations();
        this.sportRecommendations = new SportRecommendations();
        this.trackingObjectController = TrackingObjectController.getInstance();
    }

    /**
     * Checks if a recommendation for a specific user is needed or not.
     *
     * The user only gets a recommendation if
     * it is not in dnd-mode,
     * it didn't get a recommendation of the same type for more then one hour
     * and it tracked objects with the same type more then 2 times in the last two hours.
     * @param user Current user
     * @param type Tracking object type
     * @return True if a recommendation is needed, false if a recommendation isn't needed
     */
    public boolean needRecommendation(User user, TrackingObjectType type) {

        Date currentDate = new Date();
        double counter = 0.0;

        //no recommendation if user is in dnd-mode
        if (user.getDndUntil() != null && user.getDndUntil().after(currentDate)) {
            return false;

        //if user didn't got a recommendation of this type for more then one hour
        } else if (user.getLastRecommendations() == null || user.getLastRecommendations().get(type) == null || (user.getLastRecommendations().get(type) != null && this.getTimeDiffInHours(currentDate, user.getLastRecommendations().get(type)) >= 1)) {
            for (TrackingObject tObject : user.getTrackingObjects()) {
                if (trackingObjectController.getType(tObject.getName()) == type && tObject.isCountable()) {
                    for (Track track : tObject.getTracks()) {
                        //if user tracked objects with the same type more then 2 times in the last two hours
                        if (this.getTimeDiffInHours(currentDate, track.getDate()) <= 2) {
                            counter = counter + track.getCount();
                            if (counter > 2.0) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * To get the recommendations for a specific type.
     *
     * @param user Current user
     * @param type Tracking object type
     * @return The array of recommendations for the specific type
     */
    public String[] getRecommendationsForType(User user, TrackingObjectType type) {

        user.addLastRecommendations(type, new Date());
        ICRUDUser icrudUser = new ICRUDUserImpl();
        icrudUser.saveUser(user);

        //get recommendations for the specific type
        if (type == TrackingObjectType.CAFFEIN || type == TrackingObjectType.ALCOHOL || type == TrackingObjectType.BEAUTY) {
            return this.drinkRecommendations.getRecommendations().get(type);
        } else if (type == TrackingObjectType.SUPERFOOD || type == TrackingObjectType.CANDY || type == TrackingObjectType.FASTFOOD) {
            return this.foodRecommendations.getRecommendations().get(type);
        } else if (type == TrackingObjectType.POWER || type == TrackingObjectType.STAMINA || type == TrackingObjectType.RELAX) {
            return this.sportRecommendations.getRecommendations().get(type);
        }

        else return null;
    }

    /**
     * Calculates the difference between two dates.
     *
     * @param date1 First date (minuend)
     * @param date2 Second date (subtrahend)
     * @return The difference between first and second date
     */
    private int getTimeDiffInHours(Date date1, Date date2) {

        int diffInHours = (int)((date1.getTime() - date2.getTime()) / (1000 * 60 * 60));

        return diffInHours;
    }
}
