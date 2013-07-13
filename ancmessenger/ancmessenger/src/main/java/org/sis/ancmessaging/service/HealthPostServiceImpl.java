package org.sis.ancmessaging.service;

import java.util.List;

import org.sis.ancmessaging.dao.*;
import org.sis.ancmessaging.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthPostServiceImpl extends BaseDao implements HealthPostService { // NO_UCD

	@Autowired
	private HealthPostDao healthPostDao;
	
	@Autowired
	private HealthWorkerDao healthWorkerDao;
	
	@Autowired
	private TransporterDao transporterDao;
	
	@Autowired
	private GottDao gottDao;

  @Autowired
  private GareDao gareDao;
	
	@Override
	public boolean persist(HealthPost healthPost) {
		try {
			healthPostDao.update(healthPost);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public HealthPost findById(int postId) {
		return healthPostDao.getById(postId);
	}

	@Override
	public void addHwToHp(HealthExtensionWorker hw, HealthPost hp) {
		hp.getHealthWorkers().add(hw);
		hw.setHealthPost(hp);
		healthWorkerDao.save(hw);
	}

	@Override
	public void addTransporterToHp(Transporter transporter,
			HealthPost healthPost) {
		transporter.setHealthPost(healthPost);
		healthPost.getTransporters().add(transporter);
		transporterDao.save(transporter);
	}

	@Override
	public void addGottToHp(Gott gott, HealthPost hp) {
		hp.getGotts().add(gott);
		gott.setHealthPost(hp);
		gottDao.save(gott);
	}

	@Override
	public HealthExtensionWorker findHwById(int id) {
		return healthWorkerDao.getById(id);
	}

	@Override
	public Transporter findTransporterById(int id) {
		return transporterDao.getById(id);
	}

	@Override
	public Gott findGottById(int id) {
		return gottDao.getById(id);
	}

  @Override
  public void addGareToGott(Gare gare, Gott gott) {
    gott.getGares().add(gare);
    gare.setGott(gott);
    gareDao.save(gare);
  }

  @Override
  public Gare findGareById(int gareId) {
    return gareDao.getById(gareId);
  }

  @Override
	public boolean persistGott(Gott gott) {
		try {
			gottDao.update(gott);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

  @Override
  public boolean persistGare(Gare gare) {
    try {
      gareDao.update(gare);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
	public boolean persistHealthWorker(HealthExtensionWorker hw) {
		try {
			healthWorkerDao.update(hw);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public boolean persistTransporter(Transporter transporter) {
		try {
			transporterDao.update(transporter);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<HealthPost> getAll() {
		String query = "from HealthPost";
		return healthPostDao.getByQuery(query);
	}

	@Override
	public List<HealthPost> getHealthPostsForCenter(int centerId, int rows, int page, StringBuilder sb) {
		return healthPostDao.getPaginatedList(centerId, rows, page, sb);
	}

	@Override
	public List<Gott> getGottsForHealthPost(int postId, int rows, int page, StringBuilder sb) {
		return gottDao.getPaginatedList(postId, rows, page, sb);
	}
	
	@Override
	public List<HealthExtensionWorker> getHealthWorkersForHealthPost(int postId, int rows, int page, StringBuilder sb) {
		return healthWorkerDao.getPaginatedList(postId, rows, page, sb);
	}
	
	@Override
	public List<Transporter> getTransportersForHealthPost(int postId, int rows, int page, StringBuilder sb) {
		return transporterDao.getPaginatedList(postId, rows, page, sb);
	}

	@Override
	public List<Gott> getAllGottsForHealthPost(int postId) {
		return gottDao.getAllGottsForHealthPost(postId);
	}

  @Override
  public List<Gare> getGaresForGott(int gottId, int rows, int page, StringBuilder sb) {
    return gareDao.getPaginatedList(gottId, rows, page, sb);
  }

  @Override
  public List<Gare> getAllGaresForGott(int gottId) {
    return gareDao.getAllGaresForGott(gottId);
  }
}
