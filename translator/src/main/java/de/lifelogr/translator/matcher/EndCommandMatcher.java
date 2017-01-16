package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class EndCommandMatcher extends CommandMatcher
{
    public EndCommandMatcher()
    {
        this.patterns.add("^(profil|account)\\s?(entfernen|löschen)?$");
        this.patterns.add("^(ende|entfernen)$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("end", null);
    }
}
