package org.sis.ancmessaging.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class HealthExtensionWorker implements Serializable {

	private static final long serialVersionUID = 3452288415912416233L;
	
	@Id
	@GeneratedValue
	private int workerId;
	private String fullName;
	private char sex;
	private String phoneNumber;
	
	@ManyToOne
	@JoinColumn(name = "PostId")
	private HealthPost healthPost;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthWorker")
	private List<Mother> mothers = new ArrayList<Mother>();
	
	public HealthExtensionWorker() {
		
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
	
	public HealthPost getHealthPost() {
		return healthPost;
	}
	public void setHealthPost(HealthPost healthPost) {
		this.healthPost = healthPost;
	}
	public List<Mother> getMothers() {
		return mothers;
	}
	public void setMothers(List<Mother> mothers) {
		this.mothers = mothers;
	}
	
	
}
