package de.lifelogr.bootloader;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.dbconnector.DBConnector;
import de.lifelogr.notifier.Notifier;
import de.lifelogr.notifier.memories.Memory;
import de.lifelogr.notifier.memories.Mood;
import de.lifelogr.webservice.Webservice;
import org.joda.time.DateTime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class to tie everything together and start the whole machinery!
 */
public class Bootloader
{
    public static void main(String[] args)
    {
        // Init Communicator
        Communicator c = Communicator.getInstance();

        // Start webserver
        new Thread(new Webservice()).start();

        // Start DBConnector instance
        DBConnector dbc = DBConnector.getInstance();

        // Start Notifier instance
        Notifier notifier = Notifier.getInstance();

        // Start Memory-Thread (2h interval)
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Memory(), 0, 2 * 60 * 60 * 1000);

        // Configure and start "AskForMood"-Tasks
        LocalDateTime now = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("Europe/Berlin");
        ZonedDateTime zonedNow = ZonedDateTime.of(now, currentZone);
        ZonedDateTime zonedMorning;
        ZonedDateTime zonedEvening;

        // Set time to start threads
        zonedMorning = zonedNow.withHour(8).withMinute(0).withSecond(0);
        if (zonedNow.compareTo(zonedMorning) > 0)
            zonedMorning = zonedMorning.plusDays(1);
        zonedEvening = zonedNow.withHour(19).withMinute(00).withSecond(0);
        if (zonedNow.compareTo(zonedEvening) > 0)
            zonedEvening = zonedEvening.plusDays(1);

        // Calculate delays
        Duration durationMorning = Duration.between(zonedNow, zonedMorning);
        long delayMorning = durationMorning.getSeconds();
        Duration durationEvening = Duration.between(zonedNow, zonedEvening);
        long delayEvening = durationEvening.getSeconds();

        // Start morning Mood-Thread (08:00 Uhr)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Mood(8), delayMorning,
                24*60*60, TimeUnit.SECONDS);

        // Start evening Mood-Thread (19:00 Uhr)
        scheduler.scheduleAtFixedRate(new Mood(19), delayEvening,
                24*60*60, TimeUnit.SECONDS);
    }
}
