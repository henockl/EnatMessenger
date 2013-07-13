package org.sis.ancmessaging.dao;

import org.sis.ancmessaging.domain.Gare;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 6:02 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GareDao {
  public void save(Gare gare);
  public Gare getById(int id);
  public void update(Gare	gare);
  public List<Gare> getPaginatedList(int gottId, int rows, int page, StringBuilder sb);
  public List<Gare> getAllGaresForGott(int gottId);
}
