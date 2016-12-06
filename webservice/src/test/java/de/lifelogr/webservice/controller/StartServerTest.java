package de.lifelogr.webservice.controller;

import de.lifelogr.webservice.Webservice;

/**
 * Created by micha on 06.12.2016.
 */
public class StartServerTest {
        public static void main(String args[]) {
                new Thread(new Webservice()).start();
        }
}
