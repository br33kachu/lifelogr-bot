package de.lifelogr.bootloader;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.notifier.Notifier;
import de.lifelogr.webservice.Webservice;

/**
 * Class to tie everything together and start the whole machinery!
 */
public class Bootloader
{
    public static void main(String[] args)
    {
        // Init Communicator
        Communicator c = Communicator.getInstance();

        // Start webserver
        new Thread(new Webservice()).start();

        // Put user in database
        DBConnector dbc = DBConnector.getInstance();
        Notifier notifier = Notifier.getInstance();
    }
}
