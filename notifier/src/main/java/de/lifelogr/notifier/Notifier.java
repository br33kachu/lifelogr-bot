package de.lifelogr.notifier;

import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    Informant informant = Informant.getInstance();


    private Notifier() {
        informant.register(this);
    }

    public static Notifier getInstance() {
        if (instance == null) {
            instance = new Notifier();
        }
        return instance;
    }

    @Override
    public void onInform(User user) {
        System.out.println(user.getUsername());
    }
}

