package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by micha on 29.11.2016.
 */
public class WebController {
    private ICRUDUser icrudUser;

    public WebController() {
        icrudUser = new ICRUDUserImpl();
    }

    public String getJSONDataSet(int telegramId, Date from, Date to) {
        if (from == null) {
            LocalDate now = LocalDate.now();
            now.minusMonths(1);
            from = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        if (to == null) {
            LocalDate now = LocalDate.now();
            now.plusDays(1);
            to = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        List<TrackingObject> trackingObjectList = icrudUser.getUserByTelegramId(telegramId).getTrackingObjects();

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

    public int getTelegramIdByToken(String token) {
        User user = icrudUser.getUserByToken(token);
        if (user != null) return user.getTelegramId();
        return 0;
    }

    public User getUserByTelegramId(int telegramId) {
        User user = icrudUser.getUserByTelegramId(telegramId);
        return user;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
