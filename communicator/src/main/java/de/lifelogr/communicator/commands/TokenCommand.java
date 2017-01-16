package de.lifelogr.communicator.commands;

import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

/**
 * Command: /token
 * <p>
 * BotCommand for generating an unique Token for accessing the Webinterface.
 *
 * @author Michael Pham, Marco Kretz
 */
public class TokenCommand extends BotCommand
{
    private static final String LOGTAG = "TOKENCOMMAND";
    private final ICommandRegistry commandRegistry;
    private final SecureRandom random = new SecureRandom();

    /**
     * Constructor
     *
     * @param commandRegistry
     */
    public TokenCommand(ICommandRegistry commandRegistry)
    {
        super("token", "Neues Token generieren");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        ICRUDUser icrudUser = new ICRUDUserImpl();

        // Try to find User in our database
        de.lifelogr.dbconnector.entity.User currentUser = icrudUser.getUserByTelegramId(user.getId());

        // Cancel if User has no profile yet
        if (currentUser == null) {
            return;
        }

        // Set up response message
        SendMessage msg = new SendMessage();
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);


        String webSiteToken = "lifelogr.de/token/";

        // Generate token as long as it does not yet exist (max. 20 tries)
        String token;
        int maxTries = 20;
        do {
            token = this.getToken();
            maxTries--;
        } while (icrudUser.getUserByToken(token) != null && maxTries > 0);

        currentUser.setToken(token);
        msg.setText("Token wurde erstellt \"" + token + "\". Oder gehe direkt auf die Seite: " + webSiteToken.concat(token));

        currentUser.setToken(token);

        // Token expires in 90s
        currentUser.setTokenExpirationDate(this.getExpirationDate());

        // Persist changes
        icrudUser.saveUser(currentUser);

        try {
            absSender.sendMessage(msg);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    /**
     * Generate a random 8 digits long token.
     *
     * @return Generated token
     */
    private String getToken()
    {
        return RandomStringUtils.randomNumeric(8);
    }

    /**
     * Get Date object which represents the current time + 90 seconds.
     *
     * @return Date
     */
    private Date getExpirationDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 90);

        return calendar.getTime();
    }
}
