package de.lifelogr.communicator;

import de.lifelogr.translator.Translator;
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
     * Wir für jede einzelne empfangene Nachricht ausgeführt.
     *
     * @param update Enthält User-Infos und gesendete Nachricht.
     */
    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            BotLogger.info("LIFELOG", "Neue Nachricht von " + message.getFrom().toString() + " empfangen!");

            // Enthält die Nachricht geschriebenen Text?
            if (message.hasText()) {
                String translatedCommand = new Translator().translate(message.getText());
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString());
                sendMessageRequest.setText("Das habe ich übersetzt: " + translatedCommand);

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
