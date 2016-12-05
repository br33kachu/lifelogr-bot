package de.lifelogr.dbconnector;

import de.lifelogr.dbconnector.entity.User;

/**
 * Created by christin on 05.12.16.
 */
public abstract class Observer {

    public abstract void onInform(User user);
}
