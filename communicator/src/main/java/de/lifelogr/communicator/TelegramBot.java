package de.lifelogr.communicator;

import de.lifelogr.communicator.commands.HelpCommand;
import de.lifelogr.communicator.commands.StartCommand;
import de.lifelogr.communicator.commands.TrackCommand;
import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.entity.User;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import de.lifelogr.translator.Translator;
import de.lifelogr.translator.structures.CommandParams;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * @author Marco Kretz
 */
public class TelegramBot extends TelegramLongPollingCommandBot
{
    public static final String LOGTAG = "LIFELOGRBOT";

    private final String botUsername = "syp_lifelog_bot";
    private final String botToken = "289154338:AAHw9d0vFfamnqQjIJDiCpPrXfHcRaG3cwg";

    private ICRUDUser icrudUser;

    /**
     * Konstruktor
     */
    public TelegramBot()
    {
        // Logger Einstellungen
        BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());

        // Registriere Kommandos
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        register(new StartCommand(this));
        register(new TrackCommand(this));

        // Registriere Aktion für unbekannte Kommandos
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

        this.icrudUser = new ICRUDUserImpl();
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
        // Wenn eine Nachricht empfangen wurde
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = this.icrudUser.getUserByTelegramId(update.getMessage().getFrom().getId());

            // Wenn die Nachricht Text enthält
            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId().toString());

                // Der User ist registriert und antwortet auf eine Frage
                if (user != null && user.getQuestion() != null && !user.getQuestion().isEmpty()) {
                    switch (user.getQuestion()) {
                        case "username":
                            this.icrudUser.updateField(user, "username", message.getText().trim());
                            this.icrudUser.updateField(user, "question", "");
                            sendMessage.setText("Hallo " + message.getText().trim() + "! Dein Profil wurde angelegt.\nViel Spaß mit der Nutzung des LifeLogr-Bots " + Emoji.SMILING_FACE_WITH_SMILING_EYES);
                            break;
                    }
                } else if (user != null) {
                    // Übersetzte Text in Kommando
                    CommandParams translatedCmdParams = Translator.getInstance().translate(message.getText());

                    // Wenn der Text in ein Kommando übersetzt werden konnte, verusuche das Kommando auszuführen
                    if (translatedCmdParams != null) {
                        // Hole das entsprechende Kommando
                        BotCommand cmd = this.getRegisteredCommand(translatedCmdParams.getName());

                        // Ist das Kommando im System vorhanden, führe es aus
                        if (cmd != null) {
                            cmd.execute(
                                    this,
                                    update.getMessage().getFrom(),
                                    update.getMessage().getChat(),
                                    translatedCmdParams.getParams().toArray(new String[0])
                            );

                            return;
                        }

                    } else {
                        sendMessage.setText("Sorry, das habe ich leider nicht verstanden...");
                    }

                    // Das Kommando konnte nicht gefunden werden
                    sendMessage.setText("Sorry, das habe ich leider nicht verstanden. " + Emoji.DISAPPOINTED_BUT_RELIEVED_FACE);
                } else {
                    // Der User hat noch ein Profil angelegt
                    sendMessage.enableHtml(true);
                    sendMessage.setText("Bitte lege dir zuerst ein Profil an. Gib einfach '<b>Start</b>' ein, um loszulegen!");
                }

                // Sende Nachricht an den User
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
