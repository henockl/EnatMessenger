package org.sis.ancmessaging.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class HealthExtensionWorker {
	@Id
	@GeneratedValue
	private int workerId;
	private String fullName;
	private char sex;
	private String phoneNumber;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthWorker")
	private Set<Mother> mothers = new HashSet<Mother>();
	
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
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Set<Mother> getMothers() {
		return mothers;
	}
	public void setMothers(Set<Mother> mothers) {
		this.mothers = mothers;
	}
	
}
