package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by christin on 05.12.16.
 */
public class Informant {

    private static Informant instance;
    private static List<Observer> observerList = new ArrayList<>();

    private void Informant() {}

    public static Informant getInstance() {
        if(Informant.instance == null) {
            Informant.instance = new Informant();
        }
        return Informant.instance;
    }

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
