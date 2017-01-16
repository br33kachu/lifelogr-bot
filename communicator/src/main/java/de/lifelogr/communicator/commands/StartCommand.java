package de.lifelogr.communicator.commands;

import de.lifelogr.communicator.services.Emoji;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Command: /start
 * <p>
 * BotCommand starting the profile-creation process.
 * Executed on first contact with the bot.
 *
 * @author Marco Kretz
 */
public class StartCommand extends BotCommand
{
    private static final String LOGTAG = "STARTCOMMAND";
    private final ICommandRegistry commandRegistry;

    /**
     * Constructor
     *
     * @param commandRegistry Global command-registry
     */
    public StartCommand(ICommandRegistry commandRegistry)
    {
        super("start", "Erstelle ein neues Profil.");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        ICRUDUser icrudUser = new ICRUDUserImpl();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        // Only start profile-creation if User not yet exists
        if (icrudUser.getUserByTelegramId(user.getId()) == null) {
            // Create new User-object
            de.lifelogr.dbconnector.entity.User newUser = new de.lifelogr.dbconnector.entity.User();
            newUser.setTelegramId(user.getId());
            newUser.setChatId(chat.getId());

            // Try to get the Users firstname
            if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
                newUser.setFirstName(user.getFirstName());
            }

            // Try to get the Users lastname
            if (user.getLastName() != null && !user.getLastName().isEmpty()) {
                newUser.setLastName(user.getLastName());
            }

            // Set, that the User has been asked for his nickname
            newUser.setQuestion("username");

            // Persist User
            icrudUser.saveUser(newUser);

            message.setText("Hi, schön dich zu sehen! " + Emoji.HAPPY_PERSON_RAISING_ONE_HAND + "\nWie heißt du?");
        } else {
            message.setText("Ach du, wir kennen uns doch schon " + Emoji.SMILING_FACE_WITH_OPEN_MOUTH);
        }

        try {
            absSender.sendMessage(message);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
