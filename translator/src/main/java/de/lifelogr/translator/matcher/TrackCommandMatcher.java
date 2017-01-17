package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class TrackCommandMatcher extends CommandMatcher
{
    private HashMap<String, Double> numberStringMap;

    public TrackCommandMatcher()
    {
        this.patterns.add("^(track(e)?|z(\u00E4|ae)hl(e)?)\\s(?<name>\\w+)(\\s(?<value>[-+]?[0-9]*\\.?[0-9]+))?$");
        this.patterns.add("^(ich\\s(hab(e)|hatte)?\\s)?(?<value>[-+]?[0-9]*\\.?[0-9]+|ein(e)?|zwei|drei|vier|f(\u00FC|ue)nf|sechs|sieben|acht|neun|zehn|elf|zw(\u00F6|oe)lf)\\s(?<name>\\w+)(\\s(gegessen|getrunken|verspeist))?$");
        this.patterns.add("^(mir geht(\\ses|(')?s))\\s(?<mood>[A-Za-z]{2,})$");
        this.patterns.add("^ich\\sf\u00fchle\\smich\\s(?<mood>[A-Za-z]{2,})$");

        this.numberStringMap = new HashMap<>();
        this.numberStringMap.put("ein", 1.0);
        this.numberStringMap.put("eine", 1.0);
        this.numberStringMap.put("einen", 1.0);
        this.numberStringMap.put("zwei", 2.0);
        this.numberStringMap.put("drei", 3.0);
        this.numberStringMap.put("view", 4.0);
        this.numberStringMap.put("f\u00FCnf", 5.0);
        this.numberStringMap.put("fuenf", 5.0);
        this.numberStringMap.put("sechs", 6.0);
        this.numberStringMap.put("sieben", 7.0);
        this.numberStringMap.put("acht", 8.0);
        this.numberStringMap.put("neun", 9.0);
        this.numberStringMap.put("zehn", 10.0);
        this.numberStringMap.put("elf", 11.0);
        this.numberStringMap.put("zw\u00F6lf", 12.0);
        this.numberStringMap.put("zwoelf", 12.0);
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
                Double value;
                String name;

                try {
                    name = m.group("name");
                } catch (IllegalArgumentException e) {
                    String mood = m.group("mood");

                    return new CommandParams("track", "stimmung", mood);
                }


                // Wenn Mehrzahl angegeben wurde, Einzahl verwenden
                if (name.endsWith("en") || name.endsWith("re")) {
                    name = name.substring(0, name.length() - 1);
                } else if (name.endsWith("ren")) {
                    name = name.substring(0, name.length() -2);
                }

                // Versuche, den angegebenen Wert zu ermitteln
                try {
                    value = Double.parseDouble(m.group("value"));
                } catch (NumberFormatException nfe) {
                    value = this.numberStringMap.get(m.group("value"));
                } catch (Exception e) {
                    value = 1.0;
                }

                return new CommandParams("track", name, String.valueOf(value));
            }
        }

        return null;
    }
}
