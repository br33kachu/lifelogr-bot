package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * To register an observer.
 * Notifies all registered observer.
 *
 * @author christin schlimbach
 */
public class Informant {

    private static Informant instance = null;
    private static List<Observer> observerList = new ArrayList<>();

    //constructor
    private Informant() {}

    /**
     * Implements the singelton
     *
     * @return Instance of the class
     */
    public static Informant getInstance() {
        if(instance == null) {
            instance = new Informant();
        }
        return instance;
    }

    /**
     * Register an observer.
     *
     * @param observer Observer
     */
    public void register(Observer observer) {
        if (!this.observerList.contains(observer)) {
            this.observerList.add(observer);
        }
    }

    /**
     * Notify all registered observer.
     *
     * @param user Current user
     * @param object Tracking object
     */
    public static void notifyObserver(User user, TrackingObject object) {
        for (Observer observer : Informant.observerList) {
            observer.onInform(user, object);
        }
    }
    
}
