package de.lifelogr.communicator;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by Marco Kretz
 */
public class TelegramBot extends TelegramLongPollingBot
{
    private final String botUsername = "syp_lifelog_bot";
    private final String botToken = "289154338:AAHw9d0vFfamnqQjIJDiCpPrXfHcRaG3cwg";

    /**
     * Fired when user sent a message to the bot.
     *
     * @param update Includes user info and sent message.
     */
    @Override
    public void onUpdateReceived(Update update)
    {
        // TODO: Let the message get translated by the translator and execute command.
        if (update.hasMessage()) {
            Message message = update.getMessage();
            System.out.println("Neue Nachricht von " + message.getFrom().toString() + " empfangen!");

            if (message.hasText()) {
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString());
                sendMessageRequest.setText("Du hast gesagt: " + message.getText());

                try {
                    sendMessage(sendMessageRequest);
                } catch (TelegramApiException e) {
                    BotLogger.error("LIFELOG", "Nachricht an User konnte nicht gesendet werden!");
                }
            }
        }
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
