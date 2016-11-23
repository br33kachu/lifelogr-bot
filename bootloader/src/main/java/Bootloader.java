import de.lifelogr.communicator.Communicator;
import de.lifelogr.webservice.Webservice;

public class Bootloader
{
    public static void main(String[] args)
    {
        // Init Communicator
        Communicator c = Communicator.getInstance();
        Thread wsThread = new Thread(new Webservice());
        wsThread.start();
    }
}
