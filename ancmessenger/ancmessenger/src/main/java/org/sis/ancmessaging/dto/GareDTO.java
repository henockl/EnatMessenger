package org.sis.ancmessaging.dto;

import org.sis.ancmessaging.domain.Gare;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 7:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class GareDTO {
  private int gareId;
  private String gareName;
  private int gareGottId;

  public int getGareId() {
    return gareId;
  }

  public void setGareId(int gareId) {
    this.gareId = gareId;
  }

  public String getGareName() {
    return gareName;
  }

  public void setGareName(String gareName) {
    this.gareName = gareName;
  }

  public int getGareGottId() {
    return gareGottId;
  }

  public void setGareGottId(int gareGottId) {
    this.gareGottId = gareGottId;
  }

  public Gare generateGare() {
    Gare gare = new Gare();
    gare.setGareId(gareId);
    gare.setGareName(gareName);
    return gare;
  }
}
