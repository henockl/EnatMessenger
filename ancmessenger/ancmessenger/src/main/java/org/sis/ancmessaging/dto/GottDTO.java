package org.sis.ancmessaging.dto;

import org.sis.ancmessaging.domain.Gott;

public class GottDTO {
	private int gottId;
	private String gottName;
	private int postId;
	
	public int getGottId() {
		return gottId;
	}
	public void setGottId(int gottId) {
		this.gottId = gottId;
	}
	public String getGottName() {
		return gottName;
	}
	public void setGottName(String gottName) {
		this.gottName = gottName;
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public Gott generateGott() {
		Gott gott = new Gott();
		gott.setGottId(gottId);
		gott.setGottName(gottName);
		return gott;
	}
}
