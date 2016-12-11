package de.lifelogr.communicator;

import de.lifelogr.communicator.commands.HelpCommand;
import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.translator.Translator;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * @author Marco Kretz
 */
public class TelegramBot extends TelegramLongPollingCommandBot
{
    public static final String LOGTAG = "LIFELOGRBOT";

    private final String botUsername = "syp_lifelog_bot";
    private final String botToken = "289154338:AAHw9d0vFfamnqQjIJDiCpPrXfHcRaG3cwg";

    /**
     * Konstruktor
     */
    public TelegramBot()
    {
        // Registriere Kommando "/help"
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);

        // Registriere Aktion für unbekanntes Kommando
        registerDefaultAction(((absSender, message) -> {
            SendMessage commandUnknownMessage = new SendMessage();
            commandUnknownMessage.setChatId(message.getChatId().toString());
            commandUnknownMessage.setText("Das Kommando '" + message.getText() + "' kenne ich nicht. Hier etwas Hilfe " + Emoji.AMBULANCE);
            try {
                absSender.sendMessage(commandUnknownMessage);
            } catch (TelegramApiException e) {
                BotLogger.error(LOGTAG, e);
            }
            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        }));
    }

    /**
     * Verarbeite Nachrichten, die kein Kommando enthalten.
     * Versuche den Text in ein entsprechendes Kommando umzusetzen und führe dies aus.
     *
     * @param update
     */
    @Override
    public void processNonCommandUpdate(Update update)
    {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId().toString());

                // Übersetzte Text in Kommando
                String translatedCommand = Translator.getInstance().translate(message.getText());

                if (translatedCommand != null) {
                    // Hole das entsprechende Kommando
                    BotCommand cmd = this.getRegisteredCommand(translatedCommand.split(" ")[0].substring(1));

                    if (cmd != null) {
                        cmd.execute(
                                this,
                                update.getMessage().getFrom(),
                                update.getMessage().getChat(),
                                translatedCommand.split(" ")
                        );

                        return;
                    }

                }

                sendMessage.setText("Sorry, das habe ich nicht verstanden..." + Emoji.DISAPPOINTED_BUT_RELIEVED_FACE);
                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
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

    @Override
    public void onClosing()
    {

    }
}
