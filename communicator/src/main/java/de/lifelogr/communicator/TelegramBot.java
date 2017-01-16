package de.lifelogr.communicator;

import de.lifelogr.communicator.commands.*;
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

import java.util.Random;
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

    private final String[] failMessages = {
            "Tut mir Leid, das habe ich nicht verstanden. Versuche es mal mit <b>Hilfe</b>",
            "Das konnte ich nicht verarbeiten... " + Emoji.DISAPPOINTED_BUT_RELIEVED_FACE + ". <b>/hilfe</b> gibt dir eine Übersicht meiner Fähigkeiten.",
            "Ui, das hat nicht geklappt. Hast du dich vielleicht verschrieben?"
    };

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
        register(new TokenCommand(this));
        register(new SleepCommand(this));
        register(new WakeupCommand(this));
        register(new EndCommand(this));

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
                    sendMessage.setText(this.processQuestion(user, message.getText()));
                } else if (user != null) {
                    // TODO: accept start command if no profile exists!

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

                    }

                    // Das Kommando konnte nicht gefunden werden
                    sendMessage.setText(this.failMessages[new Random().nextInt(this.failMessages.length)]);
                } else {
                    // Der User hat noch ein Profil angelegt
                    sendMessage.setText("Bitte lege dir zuerst ein Profil an. Gib einfach '<b>Start</b>' ein, um loszulegen!");
                }

                sendMessage.enableHtml(true);

                // Sende Nachricht an den User
                try {
                    sendMessage(sendMessage);
                } catch (TelegramApiException e) {
                    BotLogger.error(LOGTAG, e);
                }
            }
        }
    }

    /**
     * If a User has a pending question to be answered, process message as answer.
     *
     * @param user User who sent the message.
     * @param message Message send.
     * @return Message to be sent back to the User
     */
    private String processQuestion(User user, String message)
    {
        StringBuilder builder = new StringBuilder();

        switch (user.getQuestion()) {
            case "username":
                this.icrudUser.updateField(user, "username", message.trim());
                this.icrudUser.updateField(user, "question", "");
                builder
                        .append("Hallo ")
                        .append(message.trim())
                        .append("! Dein Profil wurde angelegt.\nViel Spaß mit der Nutzung des LifeLogr-Bots ")
                        .append(Emoji.SMILING_FACE_WITH_SMILING_EYES);
                break;
            case "deleteProfile":
                if (this.isPositiveAnswer(message)) {
                    this.icrudUser.deleteUser(user);
                    builder
                            .append("Okay, schade.. Es war schön mit dir. Vielleicht bis zum nächsten Mal! ")
                            .append(Emoji.FACE_WITH_OK_GESTURE)
                            .append(" Dein Profil wurde entfernt!");
                } else {
                    user.setQuestion(null);
                    this.icrudUser.saveUser(user);
                    builder
                            .append("Der Vorgang wurde abgebrochen!");
                }
                break;
        }

        return builder.toString();
    }

    /**
     * Check if a User-message is a positive answer to a question.
     *
     * @param answer User-message
     * @return Wether answer is positive or not
     */
    private boolean isPositiveAnswer(String answer)
    {
        return answer.toLowerCase().matches("ja|yes|klar|ok|okay");
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
