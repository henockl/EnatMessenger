package org.sis.ancmessaging.json;

import java.util.List;

import org.sis.ancmessaging.dto.HealthPostDTO;

public class CustomHealthPostResponse {
	private String page;
	private String total;
	private String records;
	private List<HealthPostDTO> rows;
	
	public CustomHealthPostResponse() {
		
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

	public List<HealthPostDTO> getRows() {
		return rows;
	}

	public void setRows(List<HealthPostDTO> rows) {
		this.rows = rows;
	}

}
