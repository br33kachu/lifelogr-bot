package de.lifelogr.communicator.commands;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;



public class HelpCommand extends BotCommand
{
    private static final String LOGTAG = "HELPCOMMAND";

    private final ICommandRegistry commandRegistry;

    public HelpCommand(ICommandRegistry commandRegistry)
    {
        super("help", "Zeige alle unterstützten Kommandos.");
        this.commandRegistry = commandRegistry;
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings)
    {

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);

        StringBuilder builder = new StringBuilder();
        builder.append("Das sind meine unterstützten Kommandos:\n\n");
        this.commandRegistry.getRegisteredCommands().forEach((command) -> builder.append("<strong>/").append(command.getCommandIdentifier()).append("</strong> - ").append(command.getDescription()).append("\n"));

        helpMessage.setText(builder.toString());

        try {
            absSender.sendMessage(helpMessage);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}