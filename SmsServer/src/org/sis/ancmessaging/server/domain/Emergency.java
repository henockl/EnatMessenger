package org.sis.ancmessaging.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Emergency {
	@Id
	@GeneratedValue
	private int emergencyId;
	private String content;
	private Date arrivedOn;
	
	@ManyToOne
	@JoinColumn(name = "seqId")
	private Mother mother;

	public int getEmergencyId() {
		return emergencyId;
	}

	public void setEmergencyId(int emergencyId) {
		this.emergencyId = emergencyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getArrivedOn() {
		return arrivedOn;
	}

	public void setArrivedOn(Date arrivedOn) {
		this.arrivedOn = arrivedOn;
	}

	public Mother getMother() {
		return mother;
	}

	public void setMother(Mother mother) {
		this.mother = mother;
	}
}
