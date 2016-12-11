package de.lifelogr.communicator.commands;

import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;

/**
 * @author marco
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

    }
}
