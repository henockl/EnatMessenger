package org.sis.ancmessaging.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Mother {
	@Id
	@GeneratedValue
	private Long seqId;
	private String MotherId;
	private String fullName;
	private Integer age;
	private Date lmp;
	private String gott;
	private Date edd;
    private Date arrivalTime;
    @Column(name = "Outcome",
            columnDefinition = "enum('Delivered at Home', 'Delivered at HC', 'Delivered at HP', 'Referred', 'Unknown')")
    private String outcome;
	
	@ManyToOne
	@JoinColumn(name = "WorkerId")
	private HealthExtensionWorker healthWorker;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mother")
	private Set<Reminder> reminders = new HashSet<Reminder>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mother")
	private Set<Emergency> emergencies = new HashSet<Emergency>();
	
	public String getMotherId() {
		return MotherId;
	}
	
	public void setMotherId(String motherId) {
		MotherId = motherId;
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
	
	public HealthExtensionWorker getHealthWorker() {
		return healthWorker;
	}
	
	public void setHealthWorker(HealthExtensionWorker healthWorker) {
		this.healthWorker = healthWorker;
	}
	public String getGott() {
		return gott;
	}
	public void setGott(String gott) {
		this.gott = gott;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Set<Reminder> getReminders() {
		return reminders;
	}

	public void setReminders(Set<Reminder> reminders) {
		this.reminders = reminders;
	}

	public Date getEdd() {
		return edd;
	}

	public void setEdd(Date edd) {
		this.edd = edd;
	}

	public Set<Emergency> getEmergencies() {
		return emergencies;
	}

	public void setEmergencies(Set<Emergency> emergencies) {
		this.emergencies = emergencies;
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
