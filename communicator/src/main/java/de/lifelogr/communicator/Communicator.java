package de.lifelogr.communicator;

/**
 * Created by marco on 23.11.2016.
 */
public class Communicator
{
    private static Communicator instance = null;
    private static TelegramBot bot = null;
    private static final Object mutex = new Object();

    private Communicator()
    {

    }

    public static Communicator getInstance()
    {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new Communicator();
                }
            }
        }

        return instance;
    }
}
