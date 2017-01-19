package de.lifelogr.translator;

import de.lifelogr.translator.matcher.*;
import de.lifelogr.translator.structures.CommandParams;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Class for translating free text into internal commands.
 *
 * @author Marco Kretz
 */
public class Translator
{
    private static Translator instance = null;
    private static final Object mutex = new Object();

    private Set<CommandMatcher> matchers;

    /**
     * Constructor.
     *
     * Add all available CommandMatchers into a single Map.
     */
    private Translator()
    {
        this.matchers = new HashSet<>();
        this.matchers.add(new HelpCommandMatcher());
        this.matchers.add(new StartCommandMatcher());
        this.matchers.add(new EndCommandMatcher());
        this.matchers.add(new WakeupCommandMatcher());
        this.matchers.add(new TokenCommandMatcher());
        this.matchers.add(new SleepCommandMatcher());
        this.matchers.add(new TrackCommandMatcher());
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
     * Translate free text sent by a User into an internal command.
     *
     * @param text Text to translate
     * @return Translated command
     */
    public CommandParams translate(String text)
    {
        // Normalize text
        final String normalizedText = text.trim().toLowerCase().replaceAll(" +", " ");

        // Try to find a CommandMatcher which matches the text
        for (CommandMatcher cm : this.matchers) {
            if (cm.matches(normalizedText)) {
                return cm.getCommandParams(normalizedText);
            }
        }

        return null;
    }
}
