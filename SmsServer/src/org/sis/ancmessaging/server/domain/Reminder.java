package org.sis.ancmessaging.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Reminder implements Serializable {
	
	private static final long serialVersionUID = 5561805550324461710L;
	
	@Id
	@GeneratedValue
	private long reminderId;
	private String content;
	private Date reminderSentOn;
    @Column(name = "Status", columnDefinition = "enum('CONFIRMED', 'UNCONFIRMED'")
    private String status;
	
	@Column(name = "ReminderType", columnDefinition = "enum('WEEKLY', 'MONTHLY')")
	private String reminderType;
	
	private Date confirmationArrivedOn;
	
	@ManyToOne
	@JoinColumn(name = "SeqId")
	private Mother mother;

	public Reminder() {
		
	}
	
	public long getMessageId() {
		return reminderId;
	}

	public void setMessageId(long messageId) {
		this.reminderId = messageId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReminderSentOn() {
		return reminderSentOn;
	}

    public long getReminderId() {
        return reminderId;
    }

    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReminderSentOn(Date reminderSentOn) {
		this.reminderSentOn = reminderSentOn;
	}

	public Mother getMother() {
		return mother;
	}

	public void setMother(Mother mother) {
		this.mother = mother;
	}

	public Date getConfirmationArrivedOn() {
		return confirmationArrivedOn;
	}

	public void setConfirmationArrivedOn(Date confirmationArrivedOn) {
		this.confirmationArrivedOn = confirmationArrivedOn;
	}

	public String getReminderType() {
		return reminderType;
	}

	public void setReminderType(String reminderType) {
		this.reminderType = reminderType;
	}
}
