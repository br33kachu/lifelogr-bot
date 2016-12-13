package de.lifelogr.communicator.services;

import de.lifelogr.dbconnector.entity.User;

/**
 * @author marco
 */
public interface ICommunicator
{
    void sendMessage(User user, String text);
}
