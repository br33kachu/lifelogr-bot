package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import de.lifelogr.webservice.StartWebServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by micha on 29.11.2016.
 */
public class WebController {
    private final Logger log = Logger.getLogger(WebController.class.getName());
    private ICRUDUser icrudUser;

    /**
     * Standardkonstruktor
     */
    public WebController() {
        icrudUser = new ICRUDUserImpl();
    }

    /**
     * Get a user objekt with the telegramId and returns the trackingobjects as a vis.js-dataset.
     *
     * @param telegramId of the user
     * @param from       date from which trackingObjects should be return
     * @param to         dta to which trackingObjects should be return
     * @return wenn ein User mit der telegramId existiert und Objekte zwischen dem from und to Datum existiert, wird ein String mit den TrackingObjekten zurückgegeben, ansonsten "[]"
     */
    public String getJSONDataSet(int telegramId, Date from, Date to) {
        if (from == null) {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date 'from' is null");
            LocalDate now = LocalDate.now();
            now = now.minusMonths(1);
            from = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date 'from': " + from.toString());
        }
        if (to == null) {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date 'to' is null");
            LocalDate now = LocalDate.now();
            now = now.plusDays(1);
            to = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date 'to': " + to.toString());
        }
        User user = getUserByTelegramId(telegramId);
        if (user != null && !user.getTrackingObjects().isEmpty()) {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "User and TrackingObjects found - start filtering)");
            List<TrackingObject> trackingObjectList = user.getTrackingObjects();

            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TrackingObject trackingObject : trackingObjectList) {
                JSONObject jsonObject;
                if (trackingObject.getCategory() == null) {
                    Double sum = 0.0;
                    for (Track track : trackingObject.getTracks()) {
                        if (track.getDate().after(from) && track.getDate().before(to)) {
                            jsonObject = new JSONObject();
                            String formattedDate = simpleDateFormat.format(track.getDate());
                            jsonObject.put("x", formattedDate);
                            sum = sum + track.getCount();
                            jsonObject.put("y", sum);
                            jsonObject.put("group", this.capitalize(trackingObject.getName()));
                            jsonArray.put(jsonObject);
                        }
                    }
                } else if (trackingObject.getCategory() == 1) {
                    for (Track track : trackingObject.getTracks()) {
                        if (track.getDate().after(from) && track.getDate().before(to)) {
                            jsonObject = new JSONObject();
                            String formattedDate = simpleDateFormat.format(track.getDate());
                            jsonObject.put("x", formattedDate);
                            jsonObject.put("y", track.getCount());
                            jsonObject.put("group", this.capitalize(trackingObject.getName()));
                            jsonArray.put(jsonObject);
                        }
                    }
                }
            }
            return jsonArray.toString();
        }
        return "[]";
    }

    /**
     * Damit ein User mit einem Token identifiziert werden kann und zurückgegben wird, wird diese Methode benötigt.
     *
     * @param token das Token, das einem User zugewiesen wird
     * @return wenn ein User mit dem Token existiert, wird die telegramId des Users zurückgegeben,
     * 0 falls kein User mit dem Token existiert,
     * -1 falls das Token abgelaufen ist
     */
    public int getTelegramIdByToken(String token) {
        User user = icrudUser.getUserByToken(token);
        if (user != null) {
            if (StartWebServer.LOGGING)
                log.log(Level.INFO, "Date Expiration: " + user.getTokenExpirationDate().toString());
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date Now: " + new Date());
            if (user.getTokenExpirationDate().after(new Date()))
                return user.getTelegramId();
            else {
                return -1;
            }
        }
        return 0;
    }

    /**
     * Ein User wird mithilfe des TelegramId zur Identifikation zurückgegeben
     * @param telegramId die TelegramId des Users, der zurückgegeben werden soll
     * @return wenn ein User mit der TelegramId existiert wird der User zurückgegeben, ansonsten null
     */
    public User getUserByTelegramId(int telegramId) {
        User user = icrudUser.getUserByTelegramId(telegramId);
        return user;
    }

    /**
     * Methode um die erste Buchstabe eines Wortes in Großbuchstaben zu ändern
     * @param line das Wort als String, dessen erste Buchstabe geändert werden soll
     * @return das Wort als String mit geänderter Anfangsbuchstabe
     */
    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
