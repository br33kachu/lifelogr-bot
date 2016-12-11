package de.lifelogr.communicator.commands;

import de.lifelogr.dbconnector.entity.Track;
import de.lifelogr.dbconnector.entity.TrackingObject;
import de.lifelogr.dbconnector.impl.ICRUDUserImpl;
import de.lifelogr.dbconnector.services.ICRUDUser;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.bots.commands.ICommandRegistry;

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
        if (arguments.length == 2 && currentUser != null) {
            String name = arguments[0].trim();
            Double count = Double.parseDouble(arguments[1]);

            TrackingObject tr = currentUser.getTrackingObjectByName(name);

            if (tr == null) {
                tr = new TrackingObject();
                tr.setName(name);
                tr.setCurrentCount(count);
                currentUser.getTrackingObjects().add(tr);
            } else {
                Track track = new Track();
                track.setCount(count);
                tr.setCurrentCount(tr.getCurrentCount() + count);
                tr.getTracks().add(track);
            }

            this.icrudUser.saveUser(currentUser);
        }
    }
}
