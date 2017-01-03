package de.lifelogr.webservice;

import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.webservice.controller.WebController;
import de.lifelogr.webservice.model.WebModel;
import spark.Spark;

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
         * GET-Pfad für die Hauptseite mit der Anmeldemaske
         */
        get("/", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/\" aufgerufen.");
            return webModel.getLogin();
        });

        /**
         * GET-Pfad für die Token-Authentifizierung. Erzeugt eine Session und speichert die telegramId in der Session,
         * falls der Token gültig ist.
         * Wenn die Anmeldung erfolgreich war, Weiterleitung zur Diagramm-Seite
         * Wenn die Anmeldung erfolglos war, Weiterleitung auf eine Fehlerseite
         */
        get("/token/:token", ((request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/token/:token\" aufgerufen.");
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
                    response.body("Status Code: 401\nToken ist ungültig!");
                    return response.body();
                }
            }
            // Session existiert bereits
            response.redirect("/diagram");
            return response.body();
        }));

        /**
         * POST-Pfad für das Token-Login über die Webseite.
         * Wenn ein Token vorhanden ist wird ein Session erstellt und ein "auth_ok" zurückgegeben.
         * Wenn kein Token in der DB gefunden wird, wird ein "auth_false" zurückgegeben und es wird kein Session erstellt.
         */
        post("/token", (request, response) -> {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Seite \"/token\" aufgerufen.");
            String token = request.body();
            response.status(200);
            response.type(" text/plain");
            response.body("auth_ok");
            // Existiert noch keine Session
            if (request.session().attributes().isEmpty()) {
                int id = webController.getTelegramIdByToken(token);
                // Benutzer mit passendem Token gefunden
                if (id != 0) {
                    // Neue Session wird erstellt
                    if (StartWebServer.LOGGING)
                        log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - User mit der ID: " + id + " gefunden. Session wird erstellt und Weiterleitung auf die Diagramm-Seite");
                    request.session(true);
                    request.session().attribute("telegramID", id);
                    return response.body();
                } else {
                    // Es existiert kein User mit dem Token - Fehlermeldung
                    if (StartWebServer.LOGGING)
                        log.log(Level.INFO, "token: \"" + token + "\" wurde uebergeben - Es existiert kein User mit dem Token");
                    response.status(401);
                    response.body("auth_false");
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
                String dataSet = webController.getJSONDataSet(telegramId, null, null);
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
