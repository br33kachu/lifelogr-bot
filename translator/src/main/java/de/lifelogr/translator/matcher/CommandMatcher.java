package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

import java.util.ArrayList;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public abstract class CommandMatcher
{
    ArrayList<String> patterns = new ArrayList<>();

    public boolean matches(String text)
    {
        for (String pattern : this.patterns) {
            if (text.matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    public abstract CommandParams getCommandParams(String text);
}
