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
public class HealthPost implements Serializable {

	private static final long serialVersionUID = 6354204701335554270L;

	@Id
	@GeneratedValue
	private int postId;
	private String postName;
	private String kebele;
	
	@ManyToOne
	@JoinColumn(name = "CenterId")
	private HealthCenter healthCenter;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthPost")
	private List<Transporter> transporters = new ArrayList<Transporter>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthPost")
	private List<HealthExtensionWorker> healthWorkers = new ArrayList<HealthExtensionWorker>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "healthPost")
	private List<Gott> gotts = new ArrayList<Gott>();
	
	public HealthPost() {
		
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getKebele() {
		return kebele;
	}

	public void setKebele(String kebele) {
		this.kebele = kebele;
	}

	public HealthCenter getHealthCenter() {
		return healthCenter;
	}

	public void setHealthCenter(HealthCenter healthCenter) {
		this.healthCenter = healthCenter;
	}

	public List<Transporter> getTransporters() {
		return transporters;
	}

	public void setTransporters(List<Transporter> transporters) {
		this.transporters = transporters;
	}

	public List<HealthExtensionWorker> getHealthWorkers() {
		return healthWorkers;
	}

	public void setHealthWorkers(List<HealthExtensionWorker> healthWorkers) {
		this.healthWorkers = healthWorkers;
	}

	public List<Gott> getGotts() {
		return gotts;
	}

	public void setGotts(List<Gott> gotts) {
		this.gotts = gotts;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"postId\":" + postId + ", \"postName\":\"" + postName + "\", \"kebele\":\"" + kebele + "\"}");
		return builder.toString();
	}
	
}
