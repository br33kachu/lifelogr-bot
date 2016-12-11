package de.lifelogr.communicator.impl;

import de.lifelogr.communicator.Communicator;
import de.lifelogr.communicator.services.ICommunicator;
import de.lifelogr.dbconnector.entity.User;

/**
 * @author marco
 */
public class ICommunicatorImpl implements ICommunicator
{
    private Communicator communicator;

    public ICommunicatorImpl()
    {
        this.communicator = Communicator.getInstance();
    }

    @Override
    public void sendMessage(User user, String text)
    {
        this.communicator.sendMessage(user.getChatId().toString(), text);
    }
}
