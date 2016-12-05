package de.lifelogr.webservice.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by micha on 29.11.2016.
 */
public class WebController {
    public WebController(){}

    public String getJSONDataSet(String token) {
        DBConnector dbc = DBConnector.getInstance();
        List<TrackingObject> trackingObjectList = dbc.getTrackingObjectByToken(token);
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
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
    }
}
