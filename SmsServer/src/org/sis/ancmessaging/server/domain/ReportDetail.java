package org.sis.ancmessaging.server.domain;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/7/12
 * Time: 6:05 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class ReportDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long detailId;

    private int q1HomeDelivery;
    private int q2HomeDelivery;
    private int q3HomeDelivery;
    private int q4HomeDelivery;

    private int q1HpDelivery;
    private int q2HpDelivery;
    private int q3HpDelivery;
    private int q4HpDelivery;

    private int q1HcDelivery;
    private int q2HcDelivery;
    private int q3HcDelivery;
    private int q4HcDelivery;

    private int q1ReferredDelivery;
    private int q2ReferredDelivery;
    private int q3ReferredDelivery;
    private int q4ReferredDelivery;

    private int q1UnknownDelivery;
    private int q2UnknownDelivery;
    private int q3UnknownDelivery;
    private int q4UnknownDelivery;

    private int q1Edd;
    private int q2Edd;
    private int q3Edd;
    private int q4Edd;

    private int q1FirstReminder;
    private int q2FirstReminder;
    private int q3FirstReminder;
    private int q4FirstReminder;

    private int q1SecondReminder;
    private int q2SecondReminder;
    private int q3SecondReminder;
    private int q4SecondReminder;

    @ManyToOne
    @JoinColumn(name = "ReportId")
    private Report report;

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public int getQ1HomeDelivery() {
        return q1HomeDelivery;
    }

    public void setQ1HomeDelivery(int q1HomeDelivery) {
        this.q1HomeDelivery = q1HomeDelivery;
    }

    public int getQ2HomeDelivery() {
        return q2HomeDelivery;
    }

    public void setQ2HomeDelivery(int q2HomeDelivery) {
        this.q2HomeDelivery = q2HomeDelivery;
    }

    public int getQ3HomeDelivery() {
        return q3HomeDelivery;
    }

    public void setQ3HomeDelivery(int q3HomeDelivery) {
        this.q3HomeDelivery = q3HomeDelivery;
    }

    public int getQ4HomeDelivery() {
        return q4HomeDelivery;
    }

    public void setQ4HomeDelivery(int q4HomeDelivery) {
        this.q4HomeDelivery = q4HomeDelivery;
    }

    public int getQ1HpDelivery() {
        return q1HpDelivery;
    }

    public void setQ1HpDelivery(int q1HpDelivery) {
        this.q1HpDelivery = q1HpDelivery;
    }

    public int getQ2HpDelivery() {
        return q2HpDelivery;
    }

    public void setQ2HpDelivery(int q2HpDelivery) {
        this.q2HpDelivery = q2HpDelivery;
    }

    public int getQ3HpDelivery() {
        return q3HpDelivery;
    }

    public void setQ3HpDelivery(int q3HpDelivery) {
        this.q3HpDelivery = q3HpDelivery;
    }

    public int getQ4HpDelivery() {
        return q4HpDelivery;
    }

    public void setQ4HpDelivery(int q4HpDelivery) {
        this.q4HpDelivery = q4HpDelivery;
    }

    public int getQ1HcDelivery() {
        return q1HcDelivery;
    }

    public void setQ1HcDelivery(int q1HcDelivery) {
        this.q1HcDelivery = q1HcDelivery;
    }

    public int getQ2HcDelivery() {
        return q2HcDelivery;
    }

    public void setQ2HcDelivery(int q2HcDelivery) {
        this.q2HcDelivery = q2HcDelivery;
    }

    public int getQ3HcDelivery() {
        return q3HcDelivery;
    }

    public void setQ3HcDelivery(int q3HcDelivery) {
        this.q3HcDelivery = q3HcDelivery;
    }

    public int getQ4HcDelivery() {
        return q4HcDelivery;
    }

    public void setQ4HcDelivery(int q4HcDelivery) {
        this.q4HcDelivery = q4HcDelivery;
    }

    public int getQ1ReferredDelivery() {
        return q1ReferredDelivery;
    }

    public void setQ1ReferredDelivery(int q1ReferredDelivery) {
        this.q1ReferredDelivery = q1ReferredDelivery;
    }

    public int getQ2ReferredDelivery() {
        return q2ReferredDelivery;
    }

    public void setQ2ReferredDelivery(int q2ReferredDelivery) {
        this.q2ReferredDelivery = q2ReferredDelivery;
    }

    public int getQ3ReferredDelivery() {
        return q3ReferredDelivery;
    }

    public void setQ3ReferredDelivery(int q3ReferredDelivery) {
        this.q3ReferredDelivery = q3ReferredDelivery;
    }

    public int getQ4ReferredDelivery() {
        return q4ReferredDelivery;
    }

    public void setQ4ReferredDelivery(int q4ReferredDelivery) {
        this.q4ReferredDelivery = q4ReferredDelivery;
    }

    public int getQ1UnknownDelivery() {
        return q1UnknownDelivery;
    }

    public void setQ1UnknownDelivery(int q1UnknownDelivery) {
        this.q1UnknownDelivery = q1UnknownDelivery;
    }

    public int getQ2UnknownDelivery() {
        return q2UnknownDelivery;
    }

    public void setQ2UnknownDelivery(int q2UnknownDelivery) {
        this.q2UnknownDelivery = q2UnknownDelivery;
    }

    public int getQ3UnknownDelivery() {
        return q3UnknownDelivery;
    }

    public void setQ3UnknownDelivery(int q3UnknownDelivery) {
        this.q3UnknownDelivery = q3UnknownDelivery;
    }

    public int getQ4UnknownDelivery() {
        return q4UnknownDelivery;
    }

    public void setQ4UnknownDelivery(int q4UnknownDelivery) {
        this.q4UnknownDelivery = q4UnknownDelivery;
    }

    public int getQ1Edd() {
        return q1Edd;
    }

    public void setQ1Edd(int q1Edd) {
        this.q1Edd = q1Edd;
    }

    public int getQ2Edd() {
        return q2Edd;
    }

    public void setQ2Edd(int q2Edd) {
        this.q2Edd = q2Edd;
    }

    public int getQ3Edd() {
        return q3Edd;
    }

    public void setQ3Edd(int q3Edd) {
        this.q3Edd = q3Edd;
    }

    public int getQ4Edd() {
        return q4Edd;
    }

    public void setQ4Edd(int q4Edd) {
        this.q4Edd = q4Edd;
    }

    public int getQ1FirstReminder() {
        return q1FirstReminder;
    }

    public void setQ1FirstReminder(int q1FirstReminder) {
        this.q1FirstReminder = q1FirstReminder;
    }

    public int getQ2FirstReminder() {
        return q2FirstReminder;
    }

    public void setQ2FirstReminder(int q2FirstReminder) {
        this.q2FirstReminder = q2FirstReminder;
    }

    public int getQ3FirstReminder() {
        return q3FirstReminder;
    }

    public void setQ3FirstReminder(int q3FirstReminder) {
        this.q3FirstReminder = q3FirstReminder;
    }

    public int getQ4FirstReminder() {
        return q4FirstReminder;
    }

    public void setQ4FirstReminder(int q4FirstReminder) {
        this.q4FirstReminder = q4FirstReminder;
    }

    public int getQ1SecondReminder() {
        return q1SecondReminder;
    }

    public void setQ1SecondReminder(int q1SecondReminder) {
        this.q1SecondReminder = q1SecondReminder;
    }

    public int getQ2SecondReminder() {
        return q2SecondReminder;
    }

    public void setQ2SecondReminder(int q2SecondReminder) {
        this.q2SecondReminder = q2SecondReminder;
    }

    public int getQ3SecondReminder() {
        return q3SecondReminder;
    }

    public void setQ3SecondReminder(int q3SecondReminder) {
        this.q3SecondReminder = q3SecondReminder;
    }

    public int getQ4SecondReminder() {
        return q4SecondReminder;
    }

    public void setQ4SecondReminder(int q4SecondReminder) {
        this.q4SecondReminder = q4SecondReminder;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
