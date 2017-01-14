package de.lifelogr.webservice;

/**
 * Created by micha on 06.12.2016.
 */
public class StartWebServer {
    public static final boolean LOGGING = true;
    public static void main(String args[]) {
        new Thread(new Webservice()).start();
    }
}
