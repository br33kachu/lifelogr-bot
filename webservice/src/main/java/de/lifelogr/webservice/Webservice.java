package de.lifelogr.webservice;

import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.WebModel;
import org.bson.types.ObjectId;

import static spark.Spark.get;

public class Webservice implements Runnable {
    @Override
    public void run() {
        System.out.println("Running spark...");
        WebController webController = new WebController();
        WebModel webModel = new WebModel();

        /**
         * MainLoginScreen where the user can send the token for authenticating
         */
        get("/", (request, response) -> webModel.getLogin());

        /**
         * Login with Token - If user with that token exists, create a session and save the objectID
         * If login is successfull -> Redirect the user to /diagram site
         * If login fails -> Send a message with wrong token
         */
        get("/login", ((request, response) -> {
            String token = request.queryParams("token");
            ObjectId id = webController.getUserIdByToken(token);
            if (id != null) {
                String ok = "{\"auth\":\"true\"}";
                if (!request.session().isNew()) {
                    System.out.println("create Session!");
                    request.session(true);
                    request.session().attribute("userID", id);
                    return ok;
                } else {
                    System.out.println("Session already exists!");
                    return ok;
                }
            } else {
                return "{\"auth\":\"false\"}";
            }
        }));

        /**
         *  Get DiagramSite - if a session exists
         */
        get("/diagram", (request, response) -> {
            ObjectId id = request.session().attribute("userID");
            String dataSet = webController.getJSONDataSet(id);
            return webModel.getDiagram(dataSet);
        });

        /**
         * Get Trackingobjects, for the sessionUser
         */
        //get("/", (req,res) -> new ModelAndView(model), "main.hbs"), new HandlebarsTemplateEngine());
        get("/getDataset", (request, response) -> {
            ObjectId id = request.session().attribute("userID");
            return webController.getJSONDataSet(id);
        });


        /**
         * Loging out and destroy Session
         * TODO Destroy the Session!
         */
        get("/test/logout", (request, response) -> {
            return "{'loggedout': 'ok'}";
        });

        // ----------- TEST METHODS FOR PROTOTYPE -------------

        /**
         * MainLoginScreen where the user can send the token for authenticating
         */
        get("/test", (request, response) -> webModel.getTestLogin());

        /**
         * Get TestDiagrams - only showing one specifig testUser
         */
        get("/test/diagram", (request, response) -> {
            ObjectId id = new ObjectId("5846e843135672de475dc4a8");
            String dataSet = webController.getJSONDataSet(id);
            return webModel.getDiagram(dataSet);
        });
    }

}
