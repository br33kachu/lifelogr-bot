package de.lifelogr.communicator.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;


/**
 * Command: /help
 * <p>
 * BotCommand for showing available commands with descriptions within the chat.
 *
 * @author Marco Kretz
 */
public class HelpCommand extends BotCommand
{
    private static final String LOGTAG = "HELPCOMMAND";
    private final ICommandRegistry commandRegistry;

    /**
     * Constructor
     *
     * @param commandRegistry Global command-registry
     */
    public HelpCommand(ICommandRegistry commandRegistry)
    {
        super("help", "Zeige alle unterstützten Kommandos.");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings)
    {

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);

        // Construct message with commands and descriptions
        StringBuilder builder = new StringBuilder();
        builder.append("Das sind meine unterstützten Kommandos:\n\n");
        this.commandRegistry.getRegisteredCommands().forEach((command) -> builder
                .append("<strong>/")
                .append(command.getCommandIdentifier())
                .append("</strong> - ")
                .append(command.getDescription())
                .append("\n"));

        helpMessage.setText(builder.toString());

        // Send message to User
        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}