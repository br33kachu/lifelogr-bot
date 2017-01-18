package de.lifelogr.communicator.commands;

import de.lifelogr.communicator.services.Emoji;
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

import java.util.*;
import java.util.stream.Stream;

/**
 * Command: /track
 * <p>
 * BotCommand for adding or updating a TrackingObject.
 *
 * @author Marco Kretz
 */
public class TrackCommand extends BotCommand
{
    private static final String LOGTAG = "TRACKCOMMAND";
    private final ICommandRegistry commandRegistry;
    private final ICRUDUser icrudUser = new ICRUDUserImpl();

    private final String[] successMessages = {
            "Alles klar! Ist gespeichert. " + Emoji.SMILING_FACE_WITH_SMILING_EYES,
            "Okay, habe ich mir notiert. " + Emoji.FACE_WITH_OK_GESTURE,
            "Check!"
    };
    private final String[] nonCountableObjects = {
            "stimmung",
            "gewicht",
            "bmi",
            "blutdruck",
            "kontostand"
    };
    private final Map<Double, List<String>> moods = new HashMap<>();

    /**
     * Constructor
     *
     * @param commandRegistry Global command registry.
     */
    public TrackCommand(ICommandRegistry commandRegistry)
    {
        super("track", "Verwalte deine Tracking-Objekte.");
        this.commandRegistry = commandRegistry;
        this.initializeMoodTable();
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments)
    {
        de.lifelogr.dbconnector.entity.User currentUser = this.icrudUser.getUserByTelegramId(user.getId());
        SendMessage msg = new SendMessage();
        msg.setChatId(chat.getId().toString());
        msg.enableHtml(true);

        if (arguments.length == 0) {
            msg.setText("Ich brauche mehr Informationen. Versuche es mal mit <b>/track Banane 1</b>");
        } else if (arguments.length > 2) {
            msg.setText("Das ist zuviel Information. Versuche es mal mit <b>/track Banane 1</b>");
        } else if (currentUser != null) {
            String name = arguments[0].trim();
            Double count;
            try {
                if (name.toLowerCase().trim().equals("stimmung")) {
                    count = this.moodToDouble(arguments[1]);
                } else {
                    count = Double.parseDouble(arguments[1]);
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                count = 1.0;
            }

            TrackingObject trackingObject = currentUser.getTrackingObjectByName(name);

            // Create new TackingObject if it not yet exists
            if (trackingObject == null) {
                trackingObject = new TrackingObject();
                trackingObject.setName(name);
                trackingObject.setCurrentCount(count);
                trackingObject.setCountable(this.isCountable(name));
                currentUser.getTrackingObjects().add(trackingObject);
            } else {
                if (this.isCountable(name)) {
                    trackingObject.setCurrentCount(trackingObject.getCurrentCount() + count);
                } else {
                    trackingObject.setCurrentCount(count);
                }
            }

            // Add Track to TrackingObject
            Track track = new Track();
            track.setCount(count);
            trackingObject.getTracks().add(track);

            // Persist changes
            this.icrudUser.saveUser(currentUser);

            msg.setText(this.successMessages[new Random().nextInt(this.successMessages.length)]);
        } else {
            msg.setText("Hey, du hast noch kein Profil, daher kannst du das leider noch nicht tun!");
        }

        try {
            absSender.sendMessage(msg);
        } catch (TelegramApiException e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    private void initializeMoodTable()
    {
        List<String> bestMoods = new ArrayList<>();
        bestMoods.add("spitze");
        bestMoods.add("super");
        bestMoods.add("bestens");
        bestMoods.add("hervorragend");
        bestMoods.add("klasse");
        bestMoods.add("prima");

        List<String> okMoods = new ArrayList<>();
        okMoods.add("ok");
        okMoods.add("okay");
        okMoods.add("gut");

        List<String> mediumMoods = new ArrayList<>();
        mediumMoods.add("normal");
        mediumMoods.add("naja");
        mediumMoods.add("joa");

        List<String> badMoods = new ArrayList<>();
        badMoods.add("schlecht");
        badMoods.add("mies");
        bestMoods.add("gestresst");

        List<String> veryBadMoods = new ArrayList<>();
        veryBadMoods.add("beschissen");
        veryBadMoods.add("scheisse");
        veryBadMoods.add("abgefuckt");

        this.moods.put(5.0, bestMoods);
        this.moods.put(4.0, okMoods);
        this.moods.put(3.0, mediumMoods);
        this.moods.put(2.0, badMoods);
        this.moods.put(1.0, veryBadMoods);
    }

    /**
     * @param name
     * @return
     */
    private boolean isCountable(String name)
    {
        for (String s : this.nonCountableObjects) {
            if (s.equals(name.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    private Double moodToDouble(String mood)
    {
        final String normlaizedMood = mood.toLowerCase().trim();
        try {
            return this.moods
                    .entrySet()
                    .parallelStream()
                    .filter(e -> e.getValue().contains(normlaizedMood))
                    .findFirst()
                    .get()
                    .getKey();
        } catch (NoSuchElementException e) {
            return 3.0;
        }
    }
}
