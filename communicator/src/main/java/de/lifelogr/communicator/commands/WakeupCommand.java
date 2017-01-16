package de.lifelogr.communicator.commands;

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
 * BotCommand for stopping DND mode.
 *
 * @author Marco Kretz
 */
public class WakeupCommand extends BotCommand
{
    private static final String LOGTAG = "WAKEUPCOMMAND";
    private final ICommandRegistry commandRegistry;

    /**
     * Constructor
     *
     * @param commandRegistry Global command-registry
     */
    public WakeupCommand(ICommandRegistry commandRegistry)
    {
        super("wakeup", "Deaktiviere den Ruhemodus.");
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

        if (currentUser.getDndUntil() != null) {
            builder.append("Hey, du hast gar keinen Ruhemodus aktiviert.");
        } else {
            currentUser.setDndUntil(null);
            icrudUser.saveUser(currentUser);

            builder.append("Alles klar, Ruhemodus wurde deaktiviert.");
        }

        message.setText(builder.toString());

        try {
            absSender.sendMessage(message);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
