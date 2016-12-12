package de.lifelogr.webservice;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.WebModel;
import spark.Spark;

import static spark.Spark.get;

public class Webservice implements Runnable {
    @Override
    public void run() {
        Spark.staticFileLocation("/public");

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
            int id = webController.getTelegramIdByToken(token);
            if (id != 0) {
                String ok = "{\"auth\":\"true\"}";
                if (!request.session().isNew()) {
                    System.out.println("create Session!");
                    request.session(true);
                    request.session().attribute("telegramID", id);
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
            int telegramId = request.session().attribute("telegramID");
            User user = webController.getUserByTelegramId(telegramId);
            String dataSet = webController.getJSONDataSet(telegramId);
            return webModel.getDiagram(dataSet, user);
        });

        /**
         * Get Trackingobjects, for the sessionUser
         */
        //get("/", (req,res) -> new ModelAndView(model), "main.hbs"), new HandlebarsTemplateEngine());
        get("/dataset/{telegramId}", (request, response) -> {
            //ObjectId id = request.session().attribute("userID");
            try {
                int telegramId = Integer.parseInt(request.params("telegramId"));
                return webController.getJSONDataSet(telegramId);
            } catch (NumberFormatException e) {
                return null;
            }
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
            int telegramId = 292994467;
            User user = webController.getUserByTelegramId(telegramId);
            String dataSet = webController.getJSONDataSet(telegramId);
            return webModel.getDiagram(dataSet, user);
        });

        get("/favicon.ico", (request, response) -> {
            response.header("Content-Type", "x-icon");
            return "/favicon.ico";
        });


    }

}
