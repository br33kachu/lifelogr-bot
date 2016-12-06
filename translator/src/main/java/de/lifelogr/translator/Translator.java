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
    public static Translator instance = null;
    private static Object mutex = new Object();

    private HelpCommandMatcher hcm;
    private StartCommandMatcher scm;
    private TrackCommandMatcher tcm;

    private Translator()
    {
        this.hcm = new HelpCommandMatcher();
        this.scm = new StartCommandMatcher();
        this.tcm = new TrackCommandMatcher();
    }

    public static Translator getInstance()
    {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new Translator();
                }
            }
        }

        return instance;
    }

    /**
     * Übersetze einen vom User gesendeten Text in das entsprechende Kommando.
     *
     * @param text Zu übersetzender Text
     * @return Übersetztes Kommando
     */
    public String translate(String text)
    {
        // Text normalisieren
        text = text.trim().toLowerCase().replaceAll(" +", " ");

        if (this.hcm.matches(text)) {
            return "/help";
        }

        if (this.scm.matches(text)) {
            return "/start";
        }

        if (this.tcm.matches(text)) {
            return tcm.getTrackingParams(text).toString();
        }

        return null;
    }
}
