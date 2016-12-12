package de.lifelogr.communicator.commands;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
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
 * @author marco
 */
public class TrackCommand extends BotCommand
{
    private static final String LOGTAG = "TRACKCOMMAND";

    private final ICommandRegistry commandRegistry;
    private final ICRUDUser icrudUser = new ICRUDUserImpl();

    /**
     * @param commandRegistry
     */
    public TrackCommand(ICommandRegistry commandRegistry)
    {
        super("track", "Verwalte deine Tracking-Objekte.");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        de.lifelogr.dbconnector.entity.User currentUser = this.icrudUser.getUserByTelegramId(user.getId());
        SendMessage msg = new SendMessage();
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);

        if (arguments.length == 0) {
            msg.setText("Ich brauch mehr Informationen. Versuche es mal mit <b>/track Banane 1</b>");
        } else if (arguments.length > 2) {
            msg.setText("Das ist zuviel Information. Versuche es mal mit <b>/track Banane 1</b>");
        } else if (currentUser != null) {
            String name = arguments[0].trim();
            Double count;
            try {
                count = Double.parseDouble(arguments[1]);
            } catch (ArrayIndexOutOfBoundsException|NumberFormatException e) {
                count = 1.0;
            }

            TrackingObject trackingObject = currentUser.getTrackingObjectByName(name);

            if (trackingObject == null) {
                trackingObject = new TrackingObject();
                trackingObject.setName(name);
                trackingObject.setCurrentCount(count);
                currentUser.getTrackingObjects().add(trackingObject);
            } else {
                trackingObject.setCurrentCount(trackingObject.getCurrentCount() + count);
            }

            // Add Track to TrackingObject
            Track track = new Track();
            track.setCount(count);
            trackingObject.getTracks().add(track);

            // Persist changes
            this.icrudUser.saveUser(currentUser);

            msg.setText("Alles klar! Ist gespeichert :)");
        } else {
            msg.setText("Jetzt weiss ich gerade nicht weiter...");
        }

        try {
            absSender.sendMessage(msg);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }
}
