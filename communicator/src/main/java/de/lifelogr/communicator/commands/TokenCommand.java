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

import java.security.SecureRandom;
import java.util.Date;

/**
 * @author micha
 */
public class TokenCommand extends BotCommand {
    private static final String LOGTAG = "TOKENCOMMAND";

    private final ICommandRegistry commandRegistry;
    private final ICRUDUser icrudUser = new ICRUDUserImpl();
    private String successMessages;

    /**
     * @param commandRegistry
     */
    public TokenCommand(ICommandRegistry commandRegistry) {
        super("token", "Erstelle ein Token.");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        de.lifelogr.dbconnector.entity.User currentUser = this.icrudUser.getUserByTelegramId(user.getId());
        SendMessage msg = new SendMessage();
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);

        if (currentUser != null) {
            String token = "00000";
            String webSiteToken = "lifelogr.de/token/";

            // Token erstellen und überprüfen ob Token bereits vorhanden ist, falls ja, neuen Token erstellen
            boolean tokenExists = false;
            ICRUDUser icrudUser = new ICRUDUserImpl();
            do {
                SecureRandom random = new SecureRandom();
                int num = random.nextInt(100000);
                if (icrudUser.getUserByToken(String.valueOf(num)) == null) {
                    tokenExists = false;
                    token = String.valueOf(num);
                }
                else tokenExists = true;
            } while (tokenExists);

            currentUser.setToken(token);
            successMessages = "Token wurde erstellt \"" + token + "\". Oder gehe direkt auf die Seite: " + webSiteToken.concat(token);

            currentUser.setToken(token);

            // Set expiration Date = now + 90s
            long ms = new Date().getTime() + 90000;
            Date expiration = new Date(ms);
            currentUser.setTokenExpirationDate(expiration);

            // Persist changes
            this.icrudUser.saveUser(currentUser);

            msg.setText(this.successMessages);
        } else {
            msg.setText("Hey, du hast noch kein Profil, daher kannst du das leider noch nicht tun!");
        }

        try {
            absSender.sendMessage(msg);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
