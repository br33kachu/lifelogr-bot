package de.lifelogr.translator.matcher;

import de.lifelogr.translator.structures.CommandParams;

/**
 * @author Marco Kretz
 * @date 04.12.2016
 */
public class WakeupCommandMatcher extends CommandMatcher
{
    public WakeupCommandMatcher()
    {
        this.patterns.add("^(ruhemodus\\s?deaktivieren|stop(pen)?|anhalten)?$");
        this.patterns.add("^(aufwachen|(auf)?wecken)$");
    }

    @Override
    public CommandParams getCommandParams(String text)
    {
        return new CommandParams("wakeup", null);
    }
}
