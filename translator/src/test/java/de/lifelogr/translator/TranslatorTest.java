package de.lifelogr.translator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by marco on 04.12.2016.
 */
public class TranslatorTest
{
    private Translator translator;

    @Before
    public void before()
    {
        this.translator = new Translator();
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
}