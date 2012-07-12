package org.sis.ancmessaging.dto;


public class EditMotherDTO {
	
	private int seqId;
	private String MotherId;
	private String fullName;
	private Integer age;
	private Integer lmpDay;
	private Integer lmpMonth;
	private Integer lmpYear;
	private Integer gestationalAge;
	private String edd;
	private String gott;
    private String outcome;
	private Integer healthPostId;
	private Integer workerId;
	
	public int getSeqId() {
		return seqId;
	}
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
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
	
	public Integer getGestationalAge() {
		return gestationalAge;
	}
	public void setGestationalAge(Integer gestationalAge) {
		this.gestationalAge = gestationalAge;
	}
	public String getEdd() {
		return edd;
	}
	public void setEdd(String edd) {
		this.edd = edd;
	}
	public String getGott() {
		return gott;
	}
	public void setGott(String gott) {
		this.gott = gott;
	}
	public Integer getWorkerId() {
		return workerId;
	}
	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}
	public Integer getLmpDay() {
		return lmpDay;
	}
	public void setLmpDay(Integer lmpDay) {
		this.lmpDay = lmpDay;
	}
	public Integer getLmpMonth() {
		return lmpMonth;
	}
	public void setLmpMonth(Integer lmpMonth) {
		this.lmpMonth = lmpMonth;
	}
	public Integer getLmpYear() {
		return lmpYear;
	}
	public void setLmpYear(Integer lmpYear) {
		this.lmpYear = lmpYear;
	}
	public Integer getHealthPostId() {
		return healthPostId;
	}
	public void setHealthPostId(Integer healthPostId) {
		this.healthPostId = healthPostId;
	}

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
