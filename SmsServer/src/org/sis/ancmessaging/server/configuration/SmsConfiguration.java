package org.sis.ancmessaging.server.configuration;

public class SmsConfiguration {
	private String comPort;
	private String modemName;
	private int baud;
	private String manufacturer;
	private String model;
	private String simPin;
	private int outgoingInterval;
	private char outgoingUnit;
	private int incomingInterval;
	private char incomingUnit;
	
	public String getComPort() {
		return comPort;
	}
	public void setComPort(String comPort) {
		this.comPort = comPort;
	}
	public String getModemName() {
		return modemName;
	}
	public void setModemName(String modemName) {
		this.modemName = modemName;
	}
	public int getBaud() {
		return baud;
	}
	public void setBaud(int baud) {
		this.baud = baud;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSimPin() {
		return simPin;
	}
	public void setSimPin(String simPin) {
		this.simPin = simPin;
	}
	public int getOutgoingInterval() {
		return outgoingInterval;
	}
	public void setOutgoingInterval(int outgoingInterval) {
		this.outgoingInterval = outgoingInterval;
	}
	public char getOutgoingUnit() {
		return outgoingUnit;
	}
	public void setOutgoingUnit(char outgoingUnit) {
		this.outgoingUnit = outgoingUnit;
	}
	public int getIncomingInterval() {
		return incomingInterval;
	}
	public void setIncomingInterval(int incomingInterval) {
		this.incomingInterval = incomingInterval;
	}
	public char getIncomingUnit() {
		return incomingUnit;
	}
	public void setIncomingUnit(char incomingUnit) {
		this.incomingUnit = incomingUnit;
	}
}
