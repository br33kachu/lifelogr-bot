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

        // Put user in database
        DBConnector dbc = DBConnector.getInstance();
        Notifier notifier = Notifier.getInstance();

        //start Memory-Thread (rate ist 2 hours)
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Memory(), 0, 2 * 60 * 60 * 1000);


        //configure Mood-Threads
        LocalDateTime now = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("Europe/Berlin");
        ZonedDateTime zonedNow = ZonedDateTime.of(now, currentZone);
        ZonedDateTime zonedMorning;
        ZonedDateTime zonedEvening;

        //set time to trigger threads
        zonedMorning = zonedNow.withHour(8).withMinute(0).withSecond(0);
        if(zonedNow.compareTo(zonedMorning) > 0)
            zonedMorning = zonedMorning.plusDays(1);
        zonedEvening = zonedNow.withHour(19).withMinute(00).withSecond(0);
        if(zonedNow.compareTo(zonedEvening) > 0)
            zonedEvening = zonedEvening.plusDays(1);

        //get delays
        Duration durationMorning = Duration.between(zonedNow, zonedMorning);
        long delayMorning = durationMorning.getSeconds();
        Duration durationEvening = Duration.between(zonedNow, zonedEvening);
        long delayEvening = durationEvening.getSeconds();

        //start morning Mood-Thread (08:00 Uhr)
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Mood(8), delayMorning,
                24*60*60, TimeUnit.SECONDS);

        //start evening Mood-Thread (19:00 Uhr)
        scheduler.scheduleAtFixedRate(new Mood(19), delayEvening,
                24*60*60, TimeUnit.SECONDS);
    }
}
