package de.lifelogr.translator.matcher;

import java.util.ArrayList;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public abstract class CommandMatcher
{
    protected ArrayList<String> patterns = new ArrayList<>();

    public boolean matches(String text)
    {
        for (String pattern : patterns) {
            if (text.matches(pattern)) {
                return true;
            }
        }

        return false;
    }
}
