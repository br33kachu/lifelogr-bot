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
 * Command: /end
 * <p>
 * BotCommand for deleting own profile.
 *
 * @author Marco Kretz
 */
public class EndCommand extends BotCommand
{
    private static final String LOGTAG = "ENDCOMMAND";
    private final ICommandRegistry commandRegistry;

    /**
     * Constructor
     *
     * @param commandRegistry Global command-registry
     */
    public EndCommand(ICommandRegistry commandRegistry)
    {
        super("end", "Lösche dein Profil.");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        ICRUDUser icrudUser = new ICRUDUserImpl();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        StringBuilder builder = new StringBuilder();

        // Cancel if User does not have a profile
        de.lifelogr.dbconnector.entity.User currentUser = icrudUser.getUserByTelegramId(user.getId());
        if (currentUser == null) {
            return;
        }

        builder.append("Bist du sicher, dass du dein Profil löschen willst? Es kann nicht wiederhergestellt werden!");
        currentUser.setQuestion("deleteProfile");
        icrudUser.saveUser(currentUser);

        message.setText(builder.toString());

        try {
            absSender.sendMessage(message);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
