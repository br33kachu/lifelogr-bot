package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class SleepCommandMatcher extends CommandMatcher
{
    private HashMap<String, Double> numberStringMap;

    public SleepCommandMatcher()
    {
        this.patterns.add("^(?<value>[1-9][0-9]*)\\s(?<unit>stunde(n)?|minute(n)|sekunde(n))\\sruhe$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        // Untersuche den Text mit allen verfügbaren /track-Patterns.
        for (String pattern : this.patterns) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(text);

            // Wurde der aktuelle Ausdruck gematcht?
            if (m.find()) {
                Integer value;
                String unit = m.group("unit");

                // Versuche, den angegebenen Wert zu ermitteln
                try {
                    value = Integer.parseInt(m.group("value"));
                } catch (NumberFormatException nfe) {
                    //value = this.numberStringMap.get(m.group("value"));
                    value = 1;
                } catch (Exception e) {
                    value = 1;
                }

                if (unit.startsWith("stunde")) {
                    unit = "h";
                } else if (unit.startsWith("minute")) {
                    unit = "m";
                } else if (unit.startsWith("sekunde")) {
                    unit = "s";
                }

                return new CommandParams("sleep", String.valueOf(value) + unit);
            }
        }

        return null;
    }
}
