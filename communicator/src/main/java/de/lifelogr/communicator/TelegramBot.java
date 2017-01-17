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
            "Das konnte ich nicht verarbeiten... " + Emoji.DISAPPOINTED_BUT_RELIEVED_FACE + ". <b>/hilfe</b> gibt dir eine \u00dcbersicht meiner F\u00e4higkeiten.",
            "Ui, das hat nicht geklappt. Hast du dich vielleicht verschrieben?"
    };

    private ICRUDUser icrudUser;

    /**
     * Constructor
     */
    public TelegramBot()
    {
        // Logger settings
        BotLogger.setLevel(Level.ALL);
        BotLogger.registerLogger(new ConsoleHandler());

        // Register commands
        HelpCommand helpCommand = new HelpCommand(this);
        register(helpCommand);
        register(new StartCommand(this));
        register(new TrackCommand(this));
        register(new TokenCommand(this));
        register(new SleepCommand(this));
        register(new WakeupCommand(this));
        register(new EndCommand(this));

        // What to do for unkown commands
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
     * Process messages which are not commands.
     * Try to translate message into command and execute it.
     *
     * @param update Incoming message
     */
    @Override
    public void processNonCommandUpdate(Update update)
    {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = this.icrudUser.getUserByTelegramId(update.getMessage().getFrom().getId());

            if (message.hasText()) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId().toString());

                // User has a profile and answers to a question
                if (user != null && user.getQuestion() != null && !user.getQuestion().isEmpty()) {
                    sendMessage.setText(this.processQuestion(update, message.getText()));
                } else if (user != null) {
                    // TODO: accept start command if no profile exists!

                    // Translate text to command
                    CommandParams translatedCmdParams = Translator.getInstance().translate(message.getText());

                    // Try to execute the command
                    if (translatedCmdParams != null) {
                        // Get command
                        BotCommand cmd = this.getRegisteredCommand(translatedCmdParams.getName());

                        // If command could be found
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

                    // Command could not be found
                    sendMessage.setText(this.failMessages[new Random().nextInt(this.failMessages.length)]);
                } else {
                    // Der User hat noch ein Profil angelegt
                    sendMessage.setText("Bitte lege dir zuerst ein Profil an. Gib einfach '<b>Start</b>' ein, um loszulegen!");
                }

                sendMessage.enableHtml(true);

                // Send message to User
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
     * @param update Update
     * @param message Message send.
     * @return Message to be sent back to the User
     */
    private String processQuestion(Update update, String message)
    {
        StringBuilder builder = new StringBuilder();
        User user = this.icrudUser.getUserByTelegramId(update.getMessage().getFrom().getId());
        String question = user.getQuestion();

        if (question.equals("username")) { // User has been asked for his username
            this.icrudUser.updateField(user, "username", message.trim());
            this.icrudUser.updateField(user, "question", null);
            builder
                    .append("Hallo ")
                    .append(message.trim())
                    .append("! Dein Profil wurde angelegt.\nViel Spa\u00df mit der Nutzung des LifeLogr-Bots ")
                    .append(Emoji.SMILING_FACE_WITH_SMILING_EYES);
        } else if (question.equals("deleteProfile")) { // User has been asked for confirmation to delete profile
            if (this.isPositiveAnswer(message)) {
                this.icrudUser.deleteUser(user);
                builder
                        .append("Okay, schade.. Es war sch\u00f6n mit dir. Vielleicht bis zum n\u00e4chsten Mal! ")
                        .append(Emoji.FACE_WITH_OK_GESTURE)
                        .append(" Dein Profil wurde entfernt!");
            } else {
                user.setQuestion(null);
                this.icrudUser.saveUser(user);
                builder.append("Der Vorgang wurde abgebrochen!");
            }
        } else if (question.startsWith("tr:")) { // User has been asked to track a specific object
            if (this.isPositiveAnswer(message)) {
                try {
                    String toName = question.split(":")[1];
                    BotCommand cmd = this.getRegisteredCommand("track");
                    String[] params = { toName };
                    cmd.execute(this, update.getMessage().getFrom(), update.getMessage().getChat(), params);
                    builder.append(Emoji.HEAVY_CHECK_MARK);
                } catch (ArrayIndexOutOfBoundsException e) {
                    BotLogger.error("QUESTION:TR", e.getMessage());
                }
            } else {
                builder.append("Okay, vielleicht frage ich die sp\u00e4ter nochmal!");
                user.setQuestion(null);
                this.icrudUser.saveUser(user);
            }

        } else if (question.equals("mood")) { // User has been asked for his mood
            user.setQuestion(null);
            this.icrudUser.saveUser(user);
            BotCommand cmd = this.getRegisteredCommand("track");
            String[] params = { "stimmung", message };
            cmd.execute(this, update.getMessage().getFrom(), update.getMessage().getChat(), params);
            builder.append(Emoji.HEAVY_CHECK_MARK);
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
