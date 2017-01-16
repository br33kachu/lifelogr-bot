package de.lifelogr.webservice.model;

import de.lifelogr.dbconnector.entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class creates is required to build html-codes from models
 */
public class WebModel {

    /**
     * Creates a login-site
     *
     * @return html-string of the login-site
     */
    public String getLogin() {
        Map<String, String> model = new HashMap<>();
        model.put("title", "Lgr - Login");
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(model, "login.hbs"));
        return login;
    }

    /**
     * Creates a diagram-site
     * @param items is the vis.js dataset as string
     * @param user who is logged in on the website
     * @return html-string of the diagram-site
     */
    public String getDiagram(String items, User user) {
        Map<String, String> model = new HashMap<>();

        model.put("title", "Lgr - Diagramme");
        // Set Welcome-Header
        String username;
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            username = user.getUsername();
        } else if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            username = user.getFirstName();
        } else {
            username = "Anwender";
        }
        String welcome = "Hallo " + username + "! Hier sind deine Tracks:";
        model.put("welcome", welcome);

        // Get Items and set it as 'item' element for HBS
        String itemModel = "items = ";
        itemModel = itemModel.concat(items);
        itemModel = itemModel.concat(";");
        model.put("items", itemModel);

        // Create Checkboxes for HBS
        String checkBoxes = "";
        List<String> groups = getGroups(items);
        for (String group : groups) {
            checkBoxes = checkBoxes.concat("\n" +
                    "        <div>\n" +
                    "            <input type=\"checkbox\" class=\"filled-in\" id=\"checkbox-" + group + "\" checked=\"checked\" onclick=\"onClickCheckbox(this.id);\"/>\n" +
                    "            <label for=\"checkbox-" + group + "\" id=\"labelcheckbox-" + group + "\">" + group + "</label>\n" +
                    "        </div>");
        }
        model.put("checkBoxes", checkBoxes);
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(model, "diagram.hbs"));
        return login;
    }


    /**
     * This method is necessary to summarize all trackingObject names and remove duplicates. This is needed for the
     * HTML-Checkboxes
     * @param items is the vis.js dataset as string
     * @return a list with summarized item names
     */
    private List<String> getGroups(String items) {
        JSONArray jsonArray = new JSONArray(items);
        List<String> groups = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String group = jsonObject.getString("group");
            if (groups.size() != 0) {
                boolean exists = false;
                for (int j = 0; j < groups.size() && !exists; j++) {
                    String existingGroup = groups.get(j);
                    if (group.equals(existingGroup)) exists = true;
                }
                if (!exists) groups.add(group);
            } else {
                groups.add(group);
            }
        }
        return groups;
    }
}
