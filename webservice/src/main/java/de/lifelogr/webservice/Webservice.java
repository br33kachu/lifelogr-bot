package de.lifelogr.webservice;

import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.HandlebarsTemplateEngine;
import de.lifelogr.webservice.model.WebModel;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Webservice implements Runnable
{
    @Override
    public void run() {
        //configure Spark
        port(80);

        System.out.println("Running spark...");
        WebController webController = new WebController();
        WebModel webModel = new WebModel();

        //spark.Spark.get("/" (req, res));
        get("/", (request,response) -> webModel.getLogin());

        /* Token wird übergeben und wenn Token existiert wird ein Session ID erstellt */
        get("/getSessionID/:token", ((request, response) -> {
            System.out.println("getSessionID: :token");
            return "aha";
        }));

        /* zeige diagramme mit gegebener Session ID */
        get("/diagram/:sessionID", (request,response) -> webModel.getDiagram());


        /**
         * Get Trackingobjects with the Trackingobjects, related to the token
         */
        //get("/", (req,res) -> new ModelAndView(model), "main.hbs"), new HandlebarsTemplateEngine());
        get("/getDataset/:token", (request,response) -> webController.getJSONDataSet(request.params(":token")) );
    }

}
