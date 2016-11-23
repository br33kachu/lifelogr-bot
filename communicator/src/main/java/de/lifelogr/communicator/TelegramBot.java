package de.lifelogr.communicator;

import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

/**
 * Created by Marco Kretz
 */
public class TelegramBot extends TelegramLongPollingBot
{
    private String botUsername;
    private String botToken;

    /**
     * Fired when user sent a message to the bot.
     *
     * @param update Includes user info and sent message.
     */
    @Override
    public void onUpdateReceived(Update update)
    {
        // TODO: Let the message get translated by the translator and execute command.
    }

    @Override
    public String getBotUsername()
    {
        return this.botUsername;
    }

    @Override
    public String getBotToken()
    {
        return this.botToken;
    }
}
