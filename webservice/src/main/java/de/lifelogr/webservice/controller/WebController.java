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
 * Control class for data communication between the database and the webserver
 *
 */
public class WebController {
    private final Logger log = Logger.getLogger(WebController.class.getName());
    private ICRUDUser icrudUser;

    /**
     * Standard Constructor, initalizing the ICRUDUser object
     */
    public WebController() {
        icrudUser = new ICRUDUserImpl();
    }

    /**
     * Get a user objekt with the telegramId and returns the trackingobjects as a vis.js-dataset.
     *
     * @param telegramId of the user
     * @param from       date from, which trackingObjects should be return
     * @param to         date to, which trackingObjects should be return
     * @return if user with the telegramId found and date is between from and to, returns the string dataset, otherwise "[]"
     */
    public String getJSONDataSet(int telegramId, Date from, Date to) {
        if (from == null) {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "Date 'from' is null");
            LocalDate now = LocalDate.now();
            now = now.minusWeeks(1);
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
        if (StartWebServer.LOGGING)
            log.log(Level.INFO, "Date 'from': " + from.toString() + "; Date 'to': " + to.toString());
        User user = getUserByTelegramId(telegramId);
        if (user != null && !user.getTrackingObjects().isEmpty()) {
            if (StartWebServer.LOGGING) log.log(Level.INFO, "User and TrackingObjects found - start filtering)");
            List<TrackingObject> trackingObjectList = user.getTrackingObjects();

            JSONArray jsonArray = new JSONArray();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TrackingObject trackingObject : trackingObjectList) {
                JSONObject jsonObject;
                if (trackingObject.isCountable()) {
                    Double sum = 0.0;
                    for (Track track : trackingObject.getTracks()) {
                        if (StartWebServer.LOGGING)
                            log.log(Level.INFO, "TrackingObject Date: " + track.getDate().toString());
                        if (track.getDate().after(from) && track.getDate().before(to)) {
                            if (StartWebServer.LOGGING)
                                log.log(Level.INFO, "Trackingobjekt " + trackingObject.getName() + " added");
                            jsonObject = new JSONObject();
                            String formattedDate = simpleDateFormat.format(track.getDate());
                            jsonObject.put("x", formattedDate);
                            sum = sum + track.getCount();
                            jsonObject.put("y", sum);
                            jsonObject.put("group", this.capitalize(trackingObject.getName()));
                            jsonArray.put(jsonObject);
                        }
                    }
                } else {
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
     * Identify a user with a given token
     *
     * @param token which belongs to a user
     * @return the users telegramId, if a token existst, 0 if no token exists, -1 if the token is expired
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
     * Gets a user who has the telegramID
     *
     * @param telegramId of the user
     * @return the user if exist, otherwise null
     */
    public User getUserByTelegramId(int telegramId) {
        User user = icrudUser.getUserByTelegramId(telegramId);
        return user;
    }

    /**
     * Capitalizing the first letter of a word
     *
     * @param line is the word you want to capitalize
     * @return returns a capitalized word
     */
    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
