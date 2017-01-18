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
 * Sends a question every morning and evening to get the user's current mood.
 *
 * Bootloader starts this Thread.
 * @author christin schlimbach
 */
public class Mood extends TimerTask {

    private ICRUDUser icrudUser;
    private Communicator communicator;
    private int threadType =  0;

    /**
     * Sets the type of the Thread.
     *
     * @param type Thread type
     */
    public Mood(int type) {

        this.icrudUser = new ICRUDUserImpl();
        this.communicator = Communicator.getInstance();
        this.threadType = type;
    }

    /**
     * Sends a question to the user to get its current mood.
     */
    @Override
    public void run() {

        List<User> allUser = new ICRUDUserImpl().getAllUsers();

        for (User user: allUser) {
            if (user.getQuestion() == null || user.getQuestion().isEmpty()) {
                user.setQuestion("mood");
                this.icrudUser.saveUser(user);
                Date currentDate = new Date();

                //send messages
                if ((this.threadType == 8) && (user.getDndUntil() == null || currentDate.after(user.getDndUntil()))) {
                    this.communicator.sendMessage(user.getChatId().toString(), "Guten Morgen, wie geht's dir heute?");
                } else if ((this.threadType == 19) && (user.getDndUntil() == null || currentDate.after(user.getDndUntil()))) {
                    this.communicator.sendMessage(user.getChatId().toString(), "Na, wie geht es dir heute Abend?");
                }
            }
        }
    }
}
