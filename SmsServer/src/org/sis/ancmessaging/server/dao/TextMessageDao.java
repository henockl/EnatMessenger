package org.sis.ancmessaging.server.dao;

import org.sis.ancmessaging.server.domain.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 1/30/12
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TextMessageDao {
    public void save(TextMessage textMessage);
    public TextMessage getBySmscCodeAndRecipient(int smscCode, String recipient);
    public void changeStatus(TextMessage textMessage, String newStatus);
    public TextMessage getMessageInProgress(long seqId);
}
