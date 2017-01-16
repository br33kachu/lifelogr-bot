package de.lifelogr.webservice;

/**
 * This class starts the Werbservice class as a thread
 */
public class StartWebServer {
    public static final boolean LOGGING = false;

    /**
     * Main class
     *
     * @param args parameters
     */
    public static void main(String args[]) {
        new Thread(new Webservice()).start();
    }
}
