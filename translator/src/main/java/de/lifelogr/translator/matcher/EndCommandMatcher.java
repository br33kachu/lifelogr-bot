package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 */
public class EndCommandMatcher extends CommandMatcher
{
    public EndCommandMatcher()
    {
        this.patterns.add("^(profil|account)\\s?(entfernen|l\u00f6schen)?$");
        this.patterns.add("^(ende|entfernen)$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("end", null);
    }
}
