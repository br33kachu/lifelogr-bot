package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class TokenCommandMatcher extends CommandMatcher
{
    public TokenCommandMatcher()
    {
        this.patterns.add("^token(\\s(generieren|erstellen|erzeugen))?$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("token", null);
    }
}
