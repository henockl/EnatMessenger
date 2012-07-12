package org.sis.ancmessaging.json;

import java.util.List;

import org.sis.ancmessaging.dto.HealthCenterDTO;

public class CustomHealthCenterResponse {
	private String page;
	private String total;
	private String records;
	private List<HealthCenterDTO> rows;
	
	public CustomHealthCenterResponse() {
		
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public List<HealthCenterDTO> getRows() {
		return rows;
	}

	public void setRows(List<HealthCenterDTO> rows) {
		this.rows = rows;
	}

}
