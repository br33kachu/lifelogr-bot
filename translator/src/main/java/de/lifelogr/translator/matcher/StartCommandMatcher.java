package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 */
public class StartCommandMatcher extends CommandMatcher
{
    public StartCommandMatcher()
    {
        this.patterns.add("^(neu(er|es))?\\s?(profil|account)\\s?(anlegen|erstellen|starten)?$");
        this.patterns.add("^(start|starten|los|anfangen)$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("start", null);
    }
}
