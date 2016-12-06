package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 05.12.16.
 */
public class Informant {

    private static Informant instance = null;
    private static List<Observer> observerList = new ArrayList<>();

    private void Informant() {}

    public static Informant getInstance() {
        if(instance == null) {
            instance = new Informant();
        }
        return instance;
    }

    public void register(Observer observer) {
        if(!this.observerList.contains(observer)) {
            this.observerList.add(observer);
        }
    }

    public static void notifyObserver(User user) {
        for (Observer observer : Informant.observerList) {
            observer.onInform(user);
        }
    }
    
}
