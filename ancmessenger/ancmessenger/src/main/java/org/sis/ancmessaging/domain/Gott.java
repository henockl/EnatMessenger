package org.sis.ancmessaging.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "gott")
  private List<Gare> gares = new ArrayList<Gare>();
	
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

  public List<Gare> getGares() {
    return gares;
  }

  public void setGares(List<Gare> gares) {
    this.gares = gares;
  }
}
