package de.lifelogr.translator;

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
        assertEquals("/start", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToStart_01()
    {
        String userText = "Neues Profil anlegen";
        assertEquals("/start", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToStart_02()
    {
        String userText = "neuer account";
        assertEquals("/start", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToStart_03()
    {
        String userText = "starten";
        assertEquals("/start", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToStart_04()
    {
        String userText = "Profil anlegen";
        assertEquals("/start", this.translator.translate(userText));
    }

    // /help Kommando
    // ======================================================================
    @Test
    public void testTranslateToHelp_00()
    {
        String userText = "Hilfe";
        assertEquals("/help", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToHelp_01()
    {
        String userText = "anleitung";
        assertEquals("/help", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToHelp_02()
    {
        String userText = "was kannst du?";
        assertEquals("/help", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToHelp_03()
    {
        String userText = "help";
        assertEquals("/help", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToHelp_04()
    {
        String userText = "Wie geht das";
        assertEquals("/help", this.translator.translate(userText));
    }

    // /track Kommando
    // ======================================================================
    @Test
    public void testTranslateToTrack_00()
    {
        String userText = "Track Banane";
        assertEquals("/track banane 1.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_01()
    {
        String userText = "tracke Banane";
        assertEquals("/track banane 1.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_02()
    {
        String userText = "zähle Banane";
        assertEquals("/track banane 1.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_03()
    {
        String userText = "track Banane 2";
        assertEquals("/track banane 2.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_04()
    {
        String userText = "Zähle Kaffee 3";
        assertEquals("/track kaffee 3.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_05()
    {
        String userText = "Ich habe 2 Kaffee getrunken";
        assertEquals("/track kaffee 2.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_06()
    {
        String userText = "Ich habe drei Kaffee getrunken";
        assertEquals("/track kaffee 3.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_07()
    {
        String userText = "Ich habe fünf Bananen gegessen";
        assertEquals("/track banane 5.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_08()
    {
        String userText = "2.5 Kaffee getrunken";
        assertEquals("/track kaffee 2.5", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_09()
    {
        String userText = "Ich hatte 2 Bier";
        assertEquals("/track bier 2.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_10()
    {
        String userText = "1 Bier";
        assertEquals("/track bier 1.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_11()
    {
        String userText = "12 Bier getrunken";
        assertEquals("/track bier 12.0", this.translator.translate(userText));
    }

    @Test
    public void testTranslateToTrack_12()
    {
        String userText = "asdf Bier getrunken";
        assertNull(this.translator.translate(userText));
    }
}