package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class HelpCommandMatcher extends CommandMatcher
{
    public HelpCommandMatcher()
    {
        this.patterns.add("^(\\?|hilfe|help|anleitung|funktion(en)?|wie geht das|was (kannst|machst) du)(\\?)?$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("help", null);
    }
}
