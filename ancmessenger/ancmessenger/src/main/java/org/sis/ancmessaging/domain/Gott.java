package org.sis.ancmessaging.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Gott implements Serializable {
	
	private static final long serialVersionUID = 2762742645393644886L;
	
	@Id
	@GeneratedValue
	private int gottId;
	private String gottName;

	@ManyToOne
	@JoinColumn(name = "PostId")
	private HealthPost healthPost;
	
	public Gott() {
		
	}
	
	public int getGottId() {
		return gottId;
	}

	public void setGottId(int gottId) {
		this.gottId = gottId;
	}

	public String getGottName() {
		return gottName;
	}

	public void setGottName(String gottName) {
		this.gottName = gottName;
	}

	public HealthPost getHealthPost() {
		return healthPost;
	}

	public void setHealthPost(HealthPost healthPost) {
		this.healthPost = healthPost;
	}

	
	
	
	
}
