package de.lifelogr.webservice.model;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import spark.ModelAndView;

import java.io.IOException;

/**
 * Created by micha on 04.12.2016.
 */
public class WebModel {

    public String getLogin() {
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(null, "login.hbs"));
        return login;
    }

    public String getDiagram() {
        String login = new HandlebarsTemplateEngine().render(new ModelAndView(null, "diagram.hbs"));
        return login;
    }


}
