package de.lifelogr.translator;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasse zum Übersetzen von freiem Text in entsprechende Kommandos.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class Translator {
    private final String[] startCommandPatterns = {
            "^(neu(er|es))?\\s?(profil|account)\\s?(anlegen|erstellen|starten)?$",
            "^(start|starten|los|anfangen)$"
    };
    private final String helpCommandPattern = "^(hilfe|help|anleitung|funktion(en)?|wie geht das|was (kannst|machst) du)(\\?)?$";
    private final String[] trackCommandPatterns = new String[]{
            "^(track(e)?|z(ä|ae)hl(e)?)\\s(\\w+)(\\s\\d+)?$",
            "^(Ich\\shabe\\s)?(ein(e)?|zwei|drei|vier|f(ü|ue)nf|sechs|sieben|acht|neun|zehn|elf|zw(ö|oe)lf)\\s(\\w+)\\s(gegessen|getrunken)$"
    };

    /**
     * Übersetze einen vom User gesendeten Text in das entsprechende Kommando.
     *
     * @param text Zu übersetzender Text
     * @return Übersetztes Kommando
     */
    public String translate(String text) {
        // Text normalisieren
        text = text.trim().toLowerCase();

        // Versuche in /start zu übersetzen
        if (this.matchesStartCommand(text)) {
            return "/start";
        } else if (this.matchesHelpCommand(text)) {
            return "/help";
        }

        String trackingObject = this.getTrackValue(text);
        if (!trackingObject.equals("unknown object")) {
            return "/track " + trackingObject;
        }

        return "unknown";
    }

    /**
     * Überprüft, ob ein String als Kommando "/start" interpretiert werden kann.
     *
     * @param text Zu überprüfender Text
     * @return Ob der String als Kommando "/start" interpretiert wurde oder nicht.
     */
    private boolean matchesStartCommand(String text) {
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
    private boolean matchesHelpCommand(String text) {
        return text.matches(this.helpCommandPattern);
    }

    /**
     * Extrahiere den Namen sowie den Wert des TrackingObjekts.
     *
     * @param text Zu überprüfender Text
     * @return Name des TrackingObjekt und Anzahl
     */
    private String getTrackValue(String text) {
        String trackingObjectName;
        double trackingObjectCount;

        // Untersuche den Text mit allen verfügbaren /track-Patterns.
        for (int i = 0; i < this.trackCommandPatterns.length; i++) {
            Pattern p = Pattern.compile(this.trackCommandPatterns[i]);
            Matcher m = p.matcher(text);

            // Wurde der aktuelle Ausdruck gematcht?
            if (m.find()) {
                switch (i) {
                    case 0:
                        // Extrahiere TrackingObjekt Name
                        trackingObjectName = m.group(5);
                        if (m.group(6) != null) {
                            trackingObjectCount = Double.parseDouble(m.group(6).trim());
                        } else {
                            trackingObjectCount = 1.0;
                        }

                        return trackingObjectName + " " + trackingObjectCount;
                    case 1:
                        return m.group(6);
                }
            }
        }

        return "unknown object";
    }
}
