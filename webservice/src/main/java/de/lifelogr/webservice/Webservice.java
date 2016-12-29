package de.lifelogr.webservice;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.WebModel;
import spark.Spark;

import java.util.Date;

import static spark.Spark.*;

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
         * GET Login with Token - If user with that token exists, create a session and save the telegramId
         * If login is successfull -> Redirect the user to /diagram site
         * If login fails -> Send a message with wrong token
         */
        get("/token/:token", ((request, response) -> {
            String token = request.params("token");
            if (request.session().attributes().isEmpty()) {
                int id = webController.getTelegramIdByToken(token);
                // Benutzer mit passendem Token gefunden
                if (id != 0) {
                    System.out.println("create Session!");
                    request.session(true);
                    request.session().attribute("telegramID", id);
                    response.redirect("/diagram");
                    return response.body();
                } else {
                    // Kein User mit passendem Token gefunden
                    String authFail = "{\"auth\":\"false\"}";
                    System.out.println(authFail);
                    response.status(401);
                    return response.body();
                }
            }
            // Session existiert bereits
            response.redirect("/diagram");
            return response.body();
        }));

        post("/token", (request, response) -> {
            String token = request.body();
            response.type(" text/plain");
            response.body("auth_ok");
            // Existiert noch keine Session
            if (request.session().attributes().isEmpty()) {
                int id = webController.getTelegramIdByToken(token);
                // Benutzer mit passendem Token gefunden
                if (id != 0) {
                    // Neue Session wird erstellt
                    request.session(true);
                    request.session().attribute("telegramID", id);
                    return response.body();
                } else {
                    // Es existiert kein User mit dem Token - Fehlermeldung
                    response.body("auth_false");
                    return response.body();
                }
            }
            // Session existiert bereits
            return response.body();
        });

        /**
         *  Get DiagramSite - if a session exists
         */
        get("/diagram", (request, response) -> {
            if (!request.session().attributes().isEmpty()) {
                int telegramId = request.session().attribute("telegramID");
                User user = webController.getUserByTelegramId(telegramId);
                String dataSet = webController.getJSONDataSet(telegramId, null, null);
                return webModel.getDiagram(dataSet, user);
            } else {
                halt(401, "Keine Authorisierung!!");
            }
            return null;
        });

        /**
         * Get Trackingobjects, for the sessionUser
         */
        //get("/", (req,res) -> new ModelAndView(model), "main.hbs"), new HandlebarsTemplateEngine());
        get("/dataset", (request, response) -> {
            int telegramId = request.session().attribute("telegramId");
            Date from = new Date(request.queryParams("from"));
            Date to = new Date(request.queryParams("to"));
            return webController.getJSONDataSet(telegramId, from, to);
        });

        /**
         * Loging out and destroy Session
         * TODO Destroy the Session!
         */
        get("/logout", (request, response) -> {
            request.session().removeAttribute("telegramId");
            request.session().invalidate();
            response.redirect("/");
            return response.body();
        });

        // ----------- TEST METHODS FOR PROTOTYPE -------------

        get("/test/logout", (request, response) -> {
            request.session().removeAttribute("telegramId");
            return "{'loggedout': 'ok'}";
        });
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
            String dataSet = webController.getJSONDataSet(telegramId, null, null);
            return webModel.getDiagram(dataSet, user);
        });

        get("/favicon.ico", (request, response) -> {
            response.header("Content-Type", "x-icon");
            return "/favicon.ico";
        });


    }

}
