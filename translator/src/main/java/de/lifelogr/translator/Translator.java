package de.lifelogr.translator;

import de.lifelogr.translator.matcher.*;
import de.lifelogr.translator.structures.CommandParams;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Klasse zum Übersetzen von freiem Text in entsprechende Kommandos.
 *
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class Translator
{
    private static Translator instance = null;
    private static final Object mutex = new Object();

    private Set<CommandMatcher> matchers;

    private Translator()
    {
        this.matchers = new HashSet<>();
        this.matchers.add(new HelpCommandMatcher());
        this.matchers.add(new StartCommandMatcher());
        this.matchers.add(new TrackCommandMatcher());
        this.matchers.add(new EndCommandMatcher());
        this.matchers.add(new WakeupCommandMatcher());
        this.matchers.add(new TokenCommandMatcher());
        this.matchers.add(new SleepCommandMatcher());
    }

    public static Translator getInstance()
    {
        if (instance == null) {
            synchronized (mutex) {
                if (instance == null) {
                    instance = new Translator();
                }
            }
        }

        return instance;
    }

    /**
     * Übersetze einen vom User gesendeten Text in das entsprechende Kommando.
     *
     * @param text Zu übersetzender Text
     * @return Übersetztes Kommando
     */
    public CommandParams translate(String text)
    {
        // Text normalisieren
        final String normalizedText = text.trim().toLowerCase().replaceAll(" +", " ");

        try {
            return this.matchers
                    .parallelStream()
                    .filter(m -> m.matches(normalizedText))
                    .findFirst()
                    .get()
                    .getCommandParams(normalizedText);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
