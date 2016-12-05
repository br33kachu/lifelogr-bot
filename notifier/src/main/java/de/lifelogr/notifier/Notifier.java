package de.lifelogr.notifier;

import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance;
    Notifier notifier = Notifier.getInstance();
    Informant informant = Informant.getInstance();

    private Notifier() {}

    public static Notifier getInstance() {
        if (Notifier.instance == null) {
            Notifier.instance = new Notifier();
        }
        return Notifier.instance;
    }

    @Override
    public void onInform(User user) {
        System.out.println(user);
    }
}

