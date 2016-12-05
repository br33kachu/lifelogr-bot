package de.lifelogr.translator;

import de.lifelogr.translator.matcher.HelpCommandMatcher;
import de.lifelogr.translator.matcher.StartCommandMatcher;
import de.lifelogr.translator.matcher.TrackCommandMatcher;

/**
 * Klasse zum Übersetzen von freiem Text in entsprechende Kommandos.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class Translator
{
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

        HelpCommandMatcher hcm = new HelpCommandMatcher();
        if (hcm.matches(text)) {
            return "/help";
        }

        StartCommandMatcher scm = new StartCommandMatcher();
        if (scm.matches(text)) {
            return "/start";
        }

        TrackCommandMatcher tcm = new TrackCommandMatcher();
        if (tcm.matches(text)) {
            return tcm.getTrackingParams(text).toString();
        } else {
            System.out.println("NOT MATCHED :(");
        }

        return null;
    }
}
