package de.lifelogr.translator;

import java.util.regex.Pattern;

/**
 * Klasse zum Übersetzen von freiem Text in entsprechende Kommandos.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class Translator
{
    private final String[] startCommandPatterns = {
            "^(neu(er|es))?\\s?(profil|account)\\s?(anlegen|erstellen|starten)?$",
            "^(start|starten)$"
    };

    /**
     * Übersetze einen vom User gesendeten Text in das entsprechende Kommando.
     *
     * @param text Zu übersetzender Text
     * @return Übersetztes Kommando
     */
    public String translate(String text)
    {
        // Text normalisieren
        text = text.trim().toLowerCase();

        for (String pattern : this.startCommandPatterns) {
            if (text.matches(pattern)) {
                return "/start";
            }
        }

        return "uknown command";
    }
}
