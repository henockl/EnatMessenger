package org.sis.ancmessaging.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transporter implements Serializable {
	
	private static final long serialVersionUID = 4398852628742269147L;
	
	@Id
	@GeneratedValue
	private int transporterId;
	private String fullName;
	private String gott;
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "PostId")
	private HealthPost healthPost;
	
	public Transporter() {
		
	}
	
	public int getTransporterId() {
		return transporterId;
	}
	
	public void setTransporterId(int transporterId) {
		this.transporterId = transporterId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getGott() {
		return gott;
	}
	
	public void setGott(String gott) {
		this.gott = gott;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public HealthPost getHealthPost() {
		return healthPost;
	}

	public void setHealthPost(HealthPost healthPost) {
		this.healthPost = healthPost;
	}

}
