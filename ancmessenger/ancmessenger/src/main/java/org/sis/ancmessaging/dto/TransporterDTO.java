package org.sis.ancmessaging.dto;

import org.sis.ancmessaging.domain.Transporter;


public class TransporterDTO {
	private int postId;
	private int transporterId;
	private String fullName;
	private String phoneNumber;
	private String gott;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGott() {
		return gott;
	}
	public void setGott(String gott) {
		this.gott = gott;
	}
	
	public Transporter generateTransporter() {
		Transporter transporter = new Transporter();
		transporter.setFullName(fullName);
		transporter.setPhoneNumber(phoneNumber);
		transporter.setGott(gott);
		transporter.setTransporterId(transporterId);
		return transporter;
	}

}
