package de.lifelogr.translator.matcher;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class HelpCommandMatcher extends CommandMatcher
{
    public HelpCommandMatcher()
    {
        this.patterns.add("^(hilfe|help|anleitung|funktion(en)?|wie geht das|was (kannst|machst) du)(\\?)?$");
    }
}
