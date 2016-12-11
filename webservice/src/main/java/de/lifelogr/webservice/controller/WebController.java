package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by micha on 29.11.2016.
 */
public class WebController {
    ICRUDUser ICRUDUser;

    public WebController() {
        ICRUDUser = new ICRUDUserImpl();
    }

    public String getJSONDataSet(ObjectId userId) {
        List<TrackingObject> trackingObjectList = ICRUDUser.getTrackingObjectByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (TrackingObject trackingObject : trackingObjectList) {
            JSONObject jsonObject;
            if (trackingObject.getCategory() == 0) {
                int sum = 0;
                // TODO Date on same day wont shown correctly FIX IT?
                for (Track track : trackingObject.getTracks()) {
                    jsonObject = new JSONObject();
                    String formattedDate = simpleDateFormat.format(track.getDate());
                    jsonObject.put("x", formattedDate);
                    sum = sum + track.getCount();
                    jsonObject.put("y", sum);
                    jsonObject.put("group", trackingObject.getName());
                    jsonArray.put(jsonObject);
                }
            } else {
                for (Track track : trackingObject.getTracks()) {
                    jsonObject = new JSONObject();
                    String formattedDate = simpleDateFormat.format(track.getDate());
                    jsonObject.put("x", formattedDate);
                    jsonObject.put("y", track.getCount());
                    jsonObject.put("group", trackingObject.getName());
                    jsonArray.put(jsonObject);
                }
            }
        }
        return jsonArray.toString();
    }

    public ObjectId getUserIdByToken(String token) {
        User user = ICRUDUser.getUserByToken(token);
        return user.getId();
    }

}
