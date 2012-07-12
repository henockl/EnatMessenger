package org.sis.ancmessaging.dto;


public class MotherDTO {
	private int seqId;
	private String motherId;
	private String fullName;
	private Integer age;
	private String edd;
    private boolean passed;
    private String healthPost;
	private String gott;
	private String workerName;
	private String workerPhone;
	private boolean firstReminder;
    private boolean secondReminder;
    //private String reminderType;
	private String deliveryStatus;
	private String firstConfirmation;
    private String secondConfirmation;
	
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
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

    public String getHealthPost() {
        return healthPost;
    }

    public void setHealthPost(String healthPost) {
        this.healthPost = healthPost;
    }

    public String getGott() {
		return gott;
	}
	public void setGott(String gott) {
		this.gott = gott;
	}
	
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getWorkerPhone() {
		return workerPhone;
	}
	public void setWorkerPhone(String workerPhone) {
		this.workerPhone = workerPhone;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	public String getEdd() {
		return edd;
	}
	
	public void setEdd(String edd) {
		this.edd = edd;
	}

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getFirstConfirmation() {
		return firstConfirmation;
	}
	public void setFirstConfirmation(String firstConfirmation) {
		this.firstConfirmation = firstConfirmation;
	}
	public boolean getFirstReminder() {
		return firstReminder;
	}
	public void setFirstReminder(boolean firstReminder) {
		this.firstReminder = firstReminder;
	}

    public boolean getSecondReminder() {
        return secondReminder;
    }

    public void setSecondReminder(boolean secondReminder) {
        this.secondReminder = secondReminder;
    }

    public String getSecondConfirmation() {
        return secondConfirmation;
    }

    public void setSecondConfirmation(String secondConfirmation) {
        this.secondConfirmation = secondConfirmation;
    }
}
