package de.lifelogr.communicator.services;

import de.lifelogr.dbconnector.entity.User;

/**
 * Interface for sending messages to Users.
 *
 * @author Marco Kretz
 */
public interface ICommunicator
{
    /**
     * Send a single message to a specific User.
     *
     * @param user Receiving User
     * @param text Message
     */
    void sendMessage(User user, String text);
}
