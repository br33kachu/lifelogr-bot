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

    @Test
    public void testTranslateToStart_00()
    {
        String userText = "Neues Profil";
        assertEquals(this.translator.translate(userText), "/start");
    }

    @Test
    public void testTranslateToStart_01()
    {
        String userText = "Neues Profil anlegen";
        assertEquals(this.translator.translate(userText), "/start");
    }

    @Test
    public void testTranslateToStart_02()
    {
        String userText = "neuer account";
        assertEquals(this.translator.translate(userText), "/start");
    }

    @Test
    public void testTranslateToStart_03()
    {
        String userText = "starten";
        assertEquals(this.translator.translate(userText), "/start");
    }

    @Test
    public void testTranslateToStart_04()
    {
        String userText = "Profil anlegen";
        assertEquals(this.translator.translate(userText), "/start");
    }
}