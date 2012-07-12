package org.sis.ancmessaging.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/7/12
 * Time: 7:21 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;
    
    private int year;
    private int fromQuarter;
    private int toQuarter;
    @Column(name = "Status", columnDefinition = "enum('PENDING', 'SENT')")
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report")
    private List<ReportDetail> reportDetails = new ArrayList<ReportDetail>();
    //private List<ReportDetail> reportDetails = new ArrayList<ReportDetail>();

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFromQuarter() {
        return fromQuarter;
    }

    public void setFromQuarter(int fromQuarter) {
        this.fromQuarter = fromQuarter;
    }

    public int getToQuarter() {
        return toQuarter;
    }

    public void setToQuarter(int toQuarter) {
        this.toQuarter = toQuarter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReportDetail> getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(List<ReportDetail> reportDetails) {
        this.reportDetails = reportDetails;
    }
}
