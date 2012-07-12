package org.sis.ancmessaging.server.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 1/30/12
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TextMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int messageId;

    private String recipient;
    private int smscCode;

    @Column(name = "MessageType", columnDefinition = "enum('MONTHLY', 'WEEKLY')")
    private String messageType;

    private Date attemptSendingOn;

    @Column(name = "Status", columnDefinition = "enum('A', 'R', 'P')")
    private String status;
    private long seqId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getSmscCode() {
        return smscCode;
    }

    public void setSmscCode(int smscCode) {
        this.smscCode = smscCode;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Date getAttemptSendingOn() {
        return attemptSendingOn;
    }

    public void setAttemptSendingOn(Date attemptSendingOn) {
        this.attemptSendingOn = attemptSendingOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSeqId() {
        return seqId;
    }

    public void setSeqId(long seqId) {
        this.seqId = seqId;
    }
}
