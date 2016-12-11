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
 *
 *
 * @author Marco Kretz
 */
public class StartCommand extends BotCommand
{
    private static final String LOGTAG = "STARTCOMMAND";

    private final ICommandRegistry commandRegistry;

    /**
     * @param commandRegistry
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

        if (icrudUser.getUserByTelegramId(user.getId()) == null) {
            de.lifelogr.dbconnector.entity.User newUser = new de.lifelogr.dbconnector.entity.User();
            newUser.setTelegramId(user.getId());
            newUser.setChatId(chat.getId());
            newUser.setQuestion("username");
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
