package de.lifelogr.notifier;

import de.lifelogr.dbconnector.Informant;
import de.lifelogr.dbconnector.Observer;
import de.lifelogr.dbconnector.entity.*;
import de.lifelogr.drink.DrinkCaffein;

/**
 * Created by Christin on 04.12.2016.
 */
public class Notifier extends Observer {

    private static Notifier instance = null;
    Informant informant = Informant.getInstance();
    DrinkCaffein drinkCaffein;


    private Notifier() {
        this.informant.register(this);
        this.drinkCaffein = new DrinkCaffein();
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

