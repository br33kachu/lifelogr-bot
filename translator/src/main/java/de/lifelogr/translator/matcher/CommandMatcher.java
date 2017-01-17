package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;
import java.util.ArrayList;

/**
 * Holds regexes for matching and extracting data from free text.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public abstract class CommandMatcher
{
    ArrayList<String> patterns = new ArrayList<>();

    /**
     * Check if a text matches one of the patterns.
     * @param text Text to check
     * @return Wether a patterns was matched or not
     */
    public boolean matches(String text)
    {
        for (String pattern : this.patterns) {
            if (text.matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get the command with arguments represented as CommandParams-object.
     * May extract command arguments from the free text.
     * @param text
     * @return
     */
    public abstract CommandParams getCommandParams(String text);
}
