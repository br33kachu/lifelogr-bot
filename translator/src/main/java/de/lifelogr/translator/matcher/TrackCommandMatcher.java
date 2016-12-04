package de.lifelogr.translator.matcher;

import de.lifelogr.translator.TrackingParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class TrackCommandMatcher extends CommandMatcher
{
    public TrackCommandMatcher()
    {
        this.patterns.add("^(track(e)?|z(ä|ae)hl(e)?)\\s(\\w+)(\\s\\d+)?$");
        this.patterns.add("^(Ich\\shabe\\s)?([1-9][0-9]*|ein(e)?|zwei|drei|vier|f(ü|ue)nf|sechs|sieben|acht|neun|zehn|elf|zw(ö|oe)lf)\\s(\\w+)\\s(gegessen|getrunken)$");
    }

    public TrackingParams getTrackingParams(String text)
    {
        String trackingObjectName;
        double trackingObjectCount;

        // Untersuche den Text mit allen verfügbaren /track-Patterns.
        for (int i = 0; i < this.patterns.size(); i++) {
            Pattern p = Pattern.compile(this.patterns.get(i));
            Matcher m = p.matcher(text);

            // Wurde der aktuelle Ausdruck gematcht?
            if (m.find()) {
                if (i == 0) {
                    // Extrahiere TrackingObjekt Name
                    trackingObjectName = m.group(5);
                    if (m.group(6) != null) {
                        trackingObjectCount = Double.parseDouble(m.group(6).trim());
                    } else {
                        trackingObjectCount = 1.0;
                    }

                    return new TrackingParams(trackingObjectName, trackingObjectCount);
                } else if (i == 1) {
                    trackingObjectName = m.group(6);
                    trackingObjectCount = Double.parseDouble(m.group(2).trim());

                    return new TrackingParams(trackingObjectName, trackingObjectCount);
                }
            }
        }

        return null;
    }
}
