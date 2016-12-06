package de.lifelogr.webservice.controller;

import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by micha on 29.11.2016.
 */
public class WebController {
    public WebController(){}

    public String getJSONDataSet(ObjectId userId) {
        DBConnector dbc = DBConnector.getInstance();
        List<TrackingObject> trackingObjectList = dbc.getTrackingObjectByUserId(userId);
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(TrackingObject trackingObject: trackingObjectList) {
            JSONObject jsonObject;
            if(trackingObject.getCategory() == 0) {
                int sum = 0;
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
        DBConnector dbc = DBConnector.getInstance();
        ObjectId objectId = dbc.getUserIdByToken(token);
        return objectId;
    }

}
