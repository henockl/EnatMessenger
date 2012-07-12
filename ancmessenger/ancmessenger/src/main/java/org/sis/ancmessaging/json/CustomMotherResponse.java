package org.sis.ancmessaging.json;

import java.util.List;

import org.sis.ancmessaging.dto.MotherDTO;

public class CustomMotherResponse {
	private String page;
	private String total;
	private String records;
	private List<MotherDTO> rows;
	
	public CustomMotherResponse() {
		
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

	public List<MotherDTO> getRows() {
		return rows;
	}

	public void setRows(List<MotherDTO> rows) {
		this.rows = rows;
	}
}
