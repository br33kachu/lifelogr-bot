package de.lifelogr.translator;

import de.lifelogr.translator.structures.CommandParams;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class TranslatorTest
{
    private Translator translator;

    @Before
    public void before()
    {
        this.translator =Translator.getInstance();
    }

    // /start Kommando
    // ======================================================================
    @Test
    public void testTranslateToStart_00()
    {
        String userText = "Neues Profil";
        CommandParams expected = new CommandParams("start", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToStart_01()
    {
        String userText = "Neues Profil anlegen";
        CommandParams expected = new CommandParams("start", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToStart_02()
    {
        String userText = "neuer account";
        CommandParams expected = new CommandParams("start", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToStart_03()
    {
        String userText = "starten";
        CommandParams expected = new CommandParams("start", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToStart_04()
    {
        String userText = "Profil anlegen";
        CommandParams expected = new CommandParams("start", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    // /help Kommando
    // ======================================================================
    @Test
    public void testTranslateToHelp_00()
    {
        String userText = "Hilfe";
        CommandParams expected = new CommandParams("help", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToHelp_01()
    {
        String userText = "anleitung";
        CommandParams expected = new CommandParams("help", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToHelp_02()
    {
        String userText = "was kannst du?";
        CommandParams expected = new CommandParams("help", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToHelp_03()
    {
        String userText = "help";
        CommandParams expected = new CommandParams("help", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void testTranslateToHelp_04()
    {
        String userText = "Wie geht das";
        CommandParams expected = new CommandParams("help", null);
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
    }

    // /track Kommando
    // ======================================================================
    @Test
    public void testTranslateToTrack_00()
    {
        String userText = "Track Banane";
        CommandParams expected = new CommandParams("track", new String[]{"banane", "1.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_01()
    {
        String userText = "tracke Banane";
        CommandParams expected = new CommandParams("track", new String[]{"banane", "1.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_02()
    {
        String userText = "zähle Banane";
        CommandParams expected = new CommandParams("track", new String[]{"banane", "1.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_03()
    {
        String userText = "track Banane 2";
        CommandParams expected = new CommandParams("track", new String[]{"banane", "2.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_04()
    {
        String userText = "Zähle Kaffee 3";
        CommandParams expected = new CommandParams("track", new String[]{"kaffee", "3.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_05()
    {
        String userText = "Ich habe 2 Kaffee getrunken";
        CommandParams expected = new CommandParams("track", new String[]{"kaffee", "2.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_06()
    {
        String userText = "Ich habe drei Kaffee getrunken";
        CommandParams expected = new CommandParams("track", new String[]{"kaffee", "3.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_07()
    {
        String userText = "Ich habe fünf Bananen gegessen";
        CommandParams expected = new CommandParams("track", new String[]{"banane", "5.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_08()
    {
        String userText = "2.5 Kaffee getrunken";
        CommandParams expected = new CommandParams("track", new String[]{"kaffee", "2.5"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_09()
    {
        String userText = "Ich hatte 2 Bier";
        CommandParams expected = new CommandParams("track", new String[]{"bier", "2.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_10()
    {
        String userText = "1 Bier";
        CommandParams expected = new CommandParams("track", new String[]{"bier", "1.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_11()
    {
        String userText = "12 Bier getrunken";
        CommandParams expected = new CommandParams("track", new String[]{"bier", "12.0"});
        CommandParams actual = this.translator.translate(userText);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParams(), actual.getParams());
    }

    @Test
    public void testTranslateToTrack_12()
    {
        String userText = "asdf Bier getrunken";
        assertNull(this.translator.translate(userText));
    }
}