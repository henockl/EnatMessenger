package org.sis.ancmessaging.json;

import java.util.List;

import org.sis.ancmessaging.dto.TransporterDTO;

public class CustomTransporterResponse {
	private String page;
	private String total;
	private String records;
	private List<TransporterDTO> rows;
	
	public CustomTransporterResponse() {
		
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

	public List<TransporterDTO> getRows() {
		return rows;
	}

	public void setRows(List<TransporterDTO> rows) {
		this.rows = rows;
	}
	
}
