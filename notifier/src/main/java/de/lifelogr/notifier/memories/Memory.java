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
 * Created by Christin on 04.12.2016.
 */
public class Memory extends TimerTask {

    private ICRUDUser icrudUser = new ICRUDUserImpl();
    private Communicator communicator = Communicator.getInstance();

    @Override
    public void run() {
        List<User> allUser = new ICRUDUserImpl().getAllUsers();

        for (User user: allUser) {
            List<TrackingObject> listTObjects = getNotableTrackingObject(user);
            if (listTObjects.isEmpty()) continue;
            TrackingObject tObject = listTObjects.get(new Random().nextInt(listTObjects.size()));
            user.setQuestion("tr:" + tObject.getName());
            this.icrudUser.saveUser(user);
            this.communicator.sendMessage(user.getChatId().toString(), "Hey, du hast heute noch nicht \"" + tObject.getName() + "\" getrackt. Soll ich das jetzt für dich erledigen?");
        }
    }

    private List<TrackingObject> getNotableTrackingObject(User user) {
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        DateTime beforeYesterday = yesterday.minusDays(1);
        Interval interval1 = new Interval(yesterday.minusHours(1), yesterday.plusHours(1));
        Interval interval2 = new Interval(beforeYesterday.minusHours(1), beforeYesterday.plusHours(1));
        Interval interval3 = new Interval(today.minusHours(1), today.plusHours(1));
        List<TrackingObject> result = new ArrayList<>();

        for (TrackingObject tObject: user.getTrackingObjects()) {
            boolean inInterval1 = false, inInterval2 = false, trackedToday = false;
            for (Track t : tObject.getTracks()) {
                if (interval1.contains(t.getDate().getTime())) {
                    inInterval1 = true;
                } else if (interval2.contains(t.getDate().getTime())) {
                    inInterval2 = true;
                } else if (interval3.contains(t.getDate().getTime())) {
                    trackedToday = true;
                }
            }
            if (inInterval1 && inInterval2 && trackedToday == false) {
                result.add(tObject);
            }
        }

        return result;
    }
}
