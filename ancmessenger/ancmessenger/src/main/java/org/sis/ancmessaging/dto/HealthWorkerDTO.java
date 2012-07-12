package org.sis.ancmessaging.dto;

import org.sis.ancmessaging.domain.HealthExtensionWorker;

public class HealthWorkerDTO {
	private int postId;
	private int workerId;
	private String fullName;
	private String phoneNumber;
	private char sex;
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getWorkerId() {
		return workerId;
	}
	public void setWorkerId(int workerId) {
		this.workerId = workerId;
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
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	public HealthExtensionWorker generateHealthWorker() {
		HealthExtensionWorker healthWorker = new HealthExtensionWorker();
		healthWorker.setFullName(fullName);
		healthWorker.setPhoneNumber(phoneNumber);
		healthWorker.setSex(sex);
		healthWorker.setWorkerId(workerId);
		return healthWorker;
	}
	
}
