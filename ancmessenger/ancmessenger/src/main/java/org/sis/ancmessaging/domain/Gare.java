package org.sis.ancmessaging.domain;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 5:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Gare {
  @Id
  @GeneratedValue
  private int gareId;

  private String gareName;

  @ManyToOne
  @JoinColumn(name = "GottId")
  private Gott gott;

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

  public Gott getGott() {
    return gott;
  }

  public void setGott(Gott gott) {
    this.gott = gott;
  }
}
