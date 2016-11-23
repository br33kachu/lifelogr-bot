package de.lifelogr.webservice;

import static spark.Spark.*;

public class Webservice implements Runnable
{
    @Override
    public void run() {
        System.out.println("Running spark...");
        get("/", (req, res) -> "Hello World!");
    }
}
