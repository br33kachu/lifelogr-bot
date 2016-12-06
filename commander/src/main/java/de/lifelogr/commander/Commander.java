package de.lifelogr.commander;

import de.lifelogr.commander.Commands.HelpCommand;
import de.lifelogr.commander.Commands.StartCommand;
import de.lifelogr.commander.Commands.TrackCommand;

/**
 * Created by bozhidar on 06/12/2016.
 */
public class Commander {
    private static Commander instance = null;

    private StartCommand startcmd;
    private HelpCommand helpcmd;
    private TrackCommand trackcmd;

    private Commander()
    {
        this.startcmd = new StartCommand();
        this.helpcmd = new HelpCommand();
        this.trackcmd = new TrackCommand();
    }

    public static Commander getInstance()
    {
        if (instance == null) {
                    instance = new Commander();
        }
        return instance;
    }
}
