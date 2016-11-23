import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.webservice.Webservice;

public class Bootloader
{
    public static void main(String[] args)
    {
        // Init Communicator
        Communicator c = Communicator.getInstance();

        // Start webserver
        Thread wsThread = new Thread(new Webservice());
        wsThread.start();

        // Put user in database
        DBConnector dbc = DBConnector.getInstance();

        User u = new User();
        u.setUsername("tester1");
        dbc.addUser(u);
    }
}
