package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.User;
import org.mongodb.morphia.annotations.PostPersist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 05.12.16.
 */
public class Informant {

    private static List<Observer> observerList = new ArrayList<>();

    public void Informant() {}

    public void register(Observer observer) {
        if(!this.observerList.contains(observer)) {
            this.observerList.add(observer);
        }
    }

    public static void notify(User user) {
        for (Observer observer : Informant.observerList) {
            observer.onInform(user);
        }
    }
    
}
