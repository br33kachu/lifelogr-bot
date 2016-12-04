package de.lifelogr.translator;

import de.lifelogr.translator.matcher.CommandMatcher;
import de.lifelogr.translator.matcher.HelpCommandMatcher;
import de.lifelogr.translator.matcher.StartCommandMatcher;
import de.lifelogr.translator.matcher.TrackCommandMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasse zum Übersetzen von freiem Text in entsprechende Kommandos.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class Translator
{
    private final String[] trackCommandPatterns = new String[]{
            "^(track(e)?|z(ä|ae)hl(e)?)\\s(\\w+)(\\s\\d+)?$",
            "^(Ich\\shabe\\s)?([1-9][0-9]*|ein(e)?|zwei|drei|vier|f(ü|ue)nf|sechs|sieben|acht|neun|zehn|elf|zw(ö|oe)lf)\\s(\\w+)\\s(gegessen|getrunken)$"
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

        HelpCommandMatcher hcm = new HelpCommandMatcher();
        if (hcm.matches(text)) {
            return "/help";
        }

        StartCommandMatcher scm = new StartCommandMatcher();
        if (scm.matches(text)) {
            return ("/start");
        }

        TrackCommandMatcher tcm = new TrackCommandMatcher();
        if (tcm.matches(text)) {
            TrackingParams params = tcm.getTrackingParams(text);
            return "/track " + params.getName() + " " + params.getValue();
        }

        return null;
    }
}
