package org.sis.ancmessaging.json;

import java.util.List;

import org.sis.ancmessaging.dto.HealthWorkerDTO;

public class CustomHealthWorkerResponse {
	private String page;
	private String total;
	private String records;
	private List<HealthWorkerDTO> rows;
	
	public CustomHealthWorkerResponse() {
		
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

	public List<HealthWorkerDTO> getRows() {
		return rows;
	}

	public void setRows(List<HealthWorkerDTO> rows) {
		this.rows = rows;
	}
}
