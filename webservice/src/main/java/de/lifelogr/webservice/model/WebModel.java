package de.lifelogr.webservice.model;

import org.json.JSONArray;
import org.json.JSONObject;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by micha on 04.12.2016.
 */
public class WebModel {

    public String getLogin() {
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(null, "login.hbs"));
        return login;
    }

    public String getTestLogin() {
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(null, "testlogin.hbs"));
        return login;
    }

    public String getDiagram(String dataSet) {
        Map<String, String> model = new HashMap<>();

        // Set Welcome-Header
        // TODO get real User and set the name
        String username = System.getProperty("user.name");
        String welcome = "Hi "+username+"! Hier sind deine tracks: ";
        model.put("welcome", welcome);

        // Get Items and set it as 'item' element for HBS
        String items = "items = ";
        items = items.concat(dataSet);
        items = items.concat(";");
        model.put("items", items);

        // Create Checkboxes for HBS
        String checkBoxes = "";
        List<String> groups = getGroups(dataSet);
        for(String group: groups) {
            checkBoxes = checkBoxes.concat("\n" +
                    "        <div class=\"row\">\n" +
                    "            <input type=\"checkbox\" class=\"filled-in\" id=\"checkbox-"+group+"\" checked=\"checked\" onclick=\"onClickCheckbox(this.id);\"/>\n" +
                    "            <label for=\"checkbox-"+group+"\" id=\"labelcheckbox-"+group+"\">"+group+"</label>\n" +
                    "        </div>");

        }
        model.put("checkBoxes", checkBoxes);
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(model, "diagram.hbs"));
        return login;
    }



    private List<String> getGroups(String items) {
        JSONArray jsonArray = new JSONArray(items);
        List<String> groups = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String group = jsonObject.getString("group");
            if(groups.size() != 0) {
                boolean exists = false;
                for (int j=0; j<groups.size() && !exists; j++) {
                    String existingGroup = groups.get(j);
                    if (group.equals(existingGroup)) exists = true;
                }
                if(!exists) groups.add(group);
            } else {
                groups.add(group);
            }
        }
        return groups;
    }
}
