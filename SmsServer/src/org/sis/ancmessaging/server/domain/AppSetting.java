package org.sis.ancmessaging.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 10/24/11
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class AppSetting {
    @Id
    @GeneratedValue
    private int settingId;
    private String comPort;
    private String modemName;
    private int baudRate;
    private String manufacturer;
    private String model;
    private String simPin;
    private int outgoingInterval;
    private char outgoingUnit;
    private int incomingInterval;
    private char incomingUnit;
    private String reportPhone;

    public AppSetting() {

    }

    public int getSettingId() {
        return settingId;
    }

    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }

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

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
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

    public String getReportPhone() {
        return reportPhone;
    }

    public void setReportPhone(String reportPhone) {
        this.reportPhone = reportPhone;
    }
}
