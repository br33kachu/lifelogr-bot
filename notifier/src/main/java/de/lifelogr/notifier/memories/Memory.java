package de.lifelogr.notifier.memories;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.*;

/**
 * Checks if a memory is needed for a specific user and then sends it to the user.
 *
 * Bootloader starts this Thread.
 * @author christin schlimbach
 */
public class Memory extends TimerTask {

    private ICRUDUser icrudUser;
    private Communicator communicator;

    //constructor
    public Memory() {

        this.icrudUser = new ICRUDUserImpl();
        this.communicator = Communicator.getInstance();
    }

    /**
     * Sends a memory(message) to the user if notable tracking objects exist.
     */
    @Override
    public void run() {

        List<User> allUser = new ICRUDUserImpl().getAllUsers();

        for (User user: allUser) {

            //get the list of notable tracking objects
            List<TrackingObject> listTObjects = getNotableTrackingObject(user);

            //go to the next user if no tracking objects to remind exist
            if (listTObjects.isEmpty()) continue;

            int random = new Random().nextInt(listTObjects.size());
            TrackingObject tObject = listTObjects.get(random);
            user.setQuestion("tr:" + tObject.getName());
            this.icrudUser.saveUser(user);
            Date currentDate = new Date();

            //send message
            if (user.getDndUntil() == null || currentDate.after(user.getDndUntil())) {
                this.communicator.sendMessage(user.getChatId().toString(), "Hey, du hast \"" + tObject.getName() + "\" heute noch nicht getrackt. Soll ich das jetzt f\u00fcr dich erledigen?");
            }
        }
    }

    /**
     * Checks if a memory for a tracking object is needed or not.
     *
     * The user only gets a memory for a tracking object if
     * the tracking object has tracks from yesterday and before yesterday in the intervall +- 1 hour (from now) and
     * if the user didn't track this tracking object today.
     * @param user Current user
     * @return List of notable tracking objects (remind the user)
     */
    private List<TrackingObject> getNotableTrackingObject(User user) {

        //create dates
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime beforeYesterday = yesterday.minusDays(1);
        //interval +- 1 hour
        Interval interval1 = new Interval(yesterday.minusHours(1), yesterday.plusHours(1));
        Interval interval2 = new Interval(beforeYesterday.minusHours(1), beforeYesterday.plusHours(1));
        Interval interval3 = new Interval(today.minusHours(1), today.plusHours(1));
        List<TrackingObject> result = new ArrayList<>();

        for (TrackingObject tObject: user.getTrackingObjects()) {
            boolean inInterval1 = false, inInterval2 = false, trackedToday = false;
            for (Track track : tObject.getTracks()) {
                if (interval1.contains(track.getDate().getTime())) {
                    inInterval1 = true;
                } else if (interval2.contains(track.getDate().getTime())) {
                    inInterval2 = true;
                } else if (interval3.contains(track.getDate().getTime())) {
                    trackedToday = true;
                }
            }
            //notable tracking object if tracks yesterday and before yesterday in intervall +- 1 hour exist and no track from today
            if (inInterval1 && inInterval2 && trackedToday == false && (tObject.isCountable() == true)) {
                result.add(tObject);
            }
        }

        return result;
    }
}
