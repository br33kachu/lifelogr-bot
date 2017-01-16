package de.lifelogr.webservice;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.WebModel;
import spark.Spark;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

public class Webservice implements Runnable {
    private final Logger log = Logger.getLogger(Webservice.class.getName());

    @Override
    public void run() {
        Spark.staticFileLocation("/public");

        System.out.println("Running spark...");
        WebController webController = new WebController();
        WebModel webModel = new WebModel();

        /**
         * GET-Path for the main-page with the login-mask
         */
        get("/", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/\" aufgerufen.");
            return webModel.getLogin();
        });

        /**
         * GET-Path for the token authentication. Creates a session and saves the telegramId in the actual user session,
         * if the token is valid. If valid, redirecting to the diagram-site. If not valid, redirect on a failpage.
         */
        get("/token/:token", ((request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/token/:token\" aufgerufen.");
            String token = request.params("token");
            if (request.session().attributes().isEmpty()) {
                int id = webController.getTelegramIdByToken(token);
                switch (id) {
                    case 0: // No user found with the connected token
                        String authFail = "{\"auth\":\"false\"}";
                        response.status(401);
                        response.body("Status Code: 401\nToken ist ungültig!");
                        return response.body();
                    case -1: // Token is expired
                        if (StartWebServer.LOGGING)
                            log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - Token ist aber abgelaufen!");
                        response.status(410);
                        response.body("auth_expired");
                        return response.body();
                    default: // User with the matched token found
                        request.session(true);
                        request.session().attribute("telegramID", id);
                        response.redirect("/diagram");
                        return response.body();
                }
            }
            // Session already exists, redirects to the diagram
            response.redirect("/diagram");
            return response.body();
        }));

        /**
         * POST-Path for the tokin-login site. If token exists, creates a session and returns "auth_ok". If no tokens
         * found, returns "auth_false" and no session will be created.
         */
        post("/token", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/token\" aufgerufen.");
            String token = request.body();
            response.status(200);
            response.type(" text/plain");
            response.body("auth_ok");
            // No session exists
            if (request.session().attributes().isEmpty()) {
                int id = webController.getTelegramIdByToken(token);
                // Benutzer mit passendem Token gefunden
                switch (id) {
                    case 0:
                        // Es existiert kein User mit dem Token - Fehlermeldung
                        if (StartWebServer.LOGGING)
                            log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - Es existiert kein User mit dem Token");
                        response.status(401);
                        response.body("auth_false");
                        return response.body();
                    case -1:
                        // Token ist abgelaufen - Fehlermeldung
                        if (StartWebServer.LOGGING)
                            log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - Token ist aber abgelaufen!");
                        response.status(410);
                        response.body("auth_expired");
                        return response.body();
                    default:
                        // Neue Session wird erstellt
                        if (StartWebServer.LOGGING)
                            log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - User mit der ID: " + id + " gefunden. Session wird erstellt und Weiterleitung auf die Diagramm-Seite");
                        request.session(true);
                        request.session().attribute("telegramID", id);
                        return response.body();
                }
            }
            // Session existiert bereits
            if (StartWebServer.LOGGING)
                log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - Session existiert bereits, Weiterleitung auf die Diagramm-Seite");
            return response.body();
        });

        /**
         *  GET-Pfad für die Diagramm-Seite. Die Seite kann nur aufgerufen werden, wenn eine Session existiert.
         */
        get("/diagram", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/diagram\" aufgerufen.");
            if (!request.session().attributes().isEmpty()) {
                if (StartWebServer.LOGGING) log.log(Level.INFO, "Session existiert und die Seite wird übergeben.");
                int telegramId = request.session().attribute("telegramID");
                User user = webController.getUserByTelegramId(telegramId);
                String dataSet;
                try {
                    DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    String strFrom = request.queryParams("from");
                    String strTo = request.queryParams("to");
                    Date from = format.parse(strFrom);
                    Date to = format.parse(strTo);
                    dataSet = webController.getJSONDataSet(telegramId, from, to);
                } catch (Exception e) {
                    dataSet = webController.getJSONDataSet(telegramId, null, null);
                }
                return webModel.getDiagram(dataSet, user);
            }
            if (StartWebServer.LOGGING)
                log.log(Level.INFO, "Session existiert nicht - 401 Fehlerseite wird übergeben.");
            response.status(401);
            response.body("Status Code: 401\nKeine Authorisierung!");
            return response.body();
        });

        /**
         * GET-Pfad um das JSON-DataSet zu erstellen und zu übergeben
         */
        get("/dataset", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/diagram\" aufgerufen.");
            if (!request.session().attributes().isEmpty()) {
                int telegramId = request.session().attribute("telegramId");
                Date from = new Date(request.queryParams("from"));
                Date to = new Date(request.queryParams("to"));
                return webController.getJSONDataSet(telegramId, from, to);
            }
            response.status(401);
            response.body("Status Code: 401\nKeine Authorisierung!");
            return response.body();
        });

        /**
         * Löscht die Session-Attribute und leitet den Nutzer weiter zur Startseite "/"
         */
        get("/logout", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/logout\" aufgerufen. Session wird gelöscht.");
            request.session().removeAttribute("telegramId");
            request.session().invalidate();
            response.redirect("/");
            return response.body();
        });
    }
}
