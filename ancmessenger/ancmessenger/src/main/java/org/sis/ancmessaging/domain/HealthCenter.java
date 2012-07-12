package org.sis.ancmessaging.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class HealthCenter implements Serializable {
	
	private static final long serialVersionUID = -5735926772167033698L;
	
	@Id
	@GeneratedValue
	private int centerId;
	private String centerName;
	private String centerPhone;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthCenter")
	private List<HealthPost> healthPosts = new ArrayList<HealthPost>();
	
	public HealthCenter() {
		
	}
	
	public int getCenterId() {
		return centerId;
	}
	
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}
	
	public String getCenterName() {
		return centerName;
	}
	
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	public List<HealthPost> getHealthPosts() {
		return healthPosts;
	}

	public void setHealthPosts(List<HealthPost> healthPosts) {
		this.healthPosts = healthPosts;
	}
	
	public String getCenterPhone() {
		return centerPhone;
	}

	public void setCenterPhone(String centerPhone) {
		this.centerPhone = centerPhone;
	}

}
