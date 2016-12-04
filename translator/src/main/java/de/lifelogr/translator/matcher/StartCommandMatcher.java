package de.lifelogr.translator.matcher;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class StartCommandMatcher extends CommandMatcher
{
    public StartCommandMatcher()
    {
        this.patterns.add("^(neu(er|es))?\\s?(profil|account)\\s?(anlegen|erstellen|starten)?$");
        this.patterns.add("^(start|starten|los|anfangen)$");
    }
}
