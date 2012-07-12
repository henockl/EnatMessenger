package org.sis.ancmessaging.service;

import java.util.List;

import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthExtensionWorker;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.domain.Transporter;

public interface HealthPostService {
	public boolean persist(HealthPost healthPost);
	public HealthPost findById(int postId);
	public void addHwToHp(HealthExtensionWorker hw, HealthPost hp);
	public void addTransporterToHp(Transporter transporter, HealthPost healthPost);
	public void addGottToHp(Gott gott, HealthPost hp);
	public HealthExtensionWorker findHwById(int workerId);
	public Transporter findTransporterById(int transporterId);
	public Gott findGottById(int gottId);
	
	public boolean persistGott(Gott gott);
	public boolean persistHealthWorker(HealthExtensionWorker hw);
	public boolean persistTransporter(Transporter transporter);
	
	public List<HealthPost> getAll();
	public List<HealthPost> getHealthPostsForCenter(int centerId, int rows, int page, StringBuilder sb);
	public List<Gott> getGottsForHealthPost(int postId, int rows, int page, StringBuilder sb);
	public List<Gott> getAllGottsForHealthPost(int postId);
	public List<HealthExtensionWorker> getHealthWorkersForHealthPost(int postId, int rows, int page, StringBuilder sb);
	public List<Transporter> getTransportersForHealthPost(int postId, int rows, int page, StringBuilder sb);
}
