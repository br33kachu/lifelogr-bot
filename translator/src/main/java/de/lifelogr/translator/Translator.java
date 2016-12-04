package de.lifelogr.translator;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    private final String helpCommandPattern = "^(hilfe|help|anleitung|funktion(en)?|wie geht das|was (kannst|machst) du)(\\?)?$";

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

        // Versuche in /start zu übersetzen
        if (this.matchesStartCommand(text)) return "/start";
        else if (this.matchesHelpCommand(text)) return "/help";

        return "uknown";
    }

    /**
     * Überprüft, ob ein String als Kommando "/start" interpretiert werden kann.
     *
     * @param text Zu überprüfender Text
     * @return Ob der String als Kommando "/start" interpretiert wurde oder nicht.
     */
    private boolean matchesStartCommand(String text)
    {
        for (String pattern : this.startCommandPatterns) {
            if (text.matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Überprüft, ob ein String als Kommando "/help" interpretiert werden kann.
     *
     * @param text Zu überprüfender Text
     * @return Ob der String als Kommando "/help" interpretiert wurde oder nicht.
     */
    private boolean matchesHelpCommand(String text)
    {
        return text.matches(this.helpCommandPattern);
    }
}
