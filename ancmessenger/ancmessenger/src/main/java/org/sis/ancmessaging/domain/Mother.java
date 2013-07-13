package org.sis.ancmessaging.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Mother implements Serializable {
	
	private static final long serialVersionUID = -1208677522100429511L;
	
	@Id
	@GeneratedValue
	private int seqId;
	private String motherId;
	private String fullName;
	private Integer age;
	private Date lmp;
	private Integer gestationalAge;
	private Date edd;
	private String gott;
  //private String gare;
  private Date arrivalTime;

    @Column(name = "Outcome",
            columnDefinition = "enum('Delivered at Home', 'Delivered at HC', 'Delivered at HP', 'Referred', 'Unknown')")
    private String outcome;
	
	@ManyToOne
	@JoinColumn(name = "WorkerId")
	private HealthExtensionWorker healthWorker;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mother")
	private List<Reminder> reminders = new ArrayList<Reminder>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mother")
	private List<Emergency> emergencies = new ArrayList<Emergency>();
	
	public Mother() {
		
	}
	
	public String getMotherId() {
		return motherId;
	}
	
	public void setMotherId(String motherId) {
		this.motherId = motherId;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Date getLmp() {
		return lmp;
	}
	
	public void setLmp(Date lmp) {
		this.lmp = lmp;
	}
	public String getGott() {
		return gott;
	}
	public void setGott(String gott) {
		this.gott = gott;
	}
    /*
  public String getGare() {
    return gare;
  }

  public void setGare(String gare) {
    this.gare = gare;
  }
      */
  public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public Date getEdd() {
		return edd;
	}

	public void setEdd(Date edd) {
		this.edd = edd;
	}

	public HealthExtensionWorker getHealthWorker() {
		return healthWorker;
	}

	public void setHealthWorker(HealthExtensionWorker healthWorker) {
		this.healthWorker = healthWorker;
	}

	public List<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(List<Reminder> reminders) {
		this.reminders = reminders;
	}

	public List<Emergency> getEmergencies() {
		return emergencies;
	}

	public void setEmergencies(List<Emergency> emergencies) {
		this.emergencies = emergencies;
	}

	public Integer getGestationalAge() {
		return gestationalAge;
	}

	public void setGestationalAge(Integer gestationalAge) {
		this.gestationalAge = gestationalAge;
	}

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
