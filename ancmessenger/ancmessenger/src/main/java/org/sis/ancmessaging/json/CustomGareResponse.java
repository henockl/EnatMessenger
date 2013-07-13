package org.sis.ancmessaging.json;

import org.sis.ancmessaging.dto.GareDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 7:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomGareResponse {
  private String page;
  private String total;
  private String records;
  private List<GareDTO> rows;

  public CustomGareResponse() {
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

  public List<GareDTO> getRows() {
    return rows;
  }

  public void setRows(List<GareDTO> rows) {
    this.rows = rows;
  }
}
