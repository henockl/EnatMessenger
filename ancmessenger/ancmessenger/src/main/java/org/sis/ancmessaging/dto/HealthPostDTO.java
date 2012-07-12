package org.sis.ancmessaging.dto;

import org.sis.ancmessaging.domain.HealthPost;

public class HealthPostDTO {
	private int centerId;
	private int postId;
	private String postName;
	private String kebele;
	
	
	public int getCenterId() {
		return centerId;
	}


	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public String getPostName() {
		return postName;
	}


	public void setPostName(String postName) {
		this.postName = postName;
	}

	public HealthPost generateHealthPost() {
		HealthPost healthPost = new HealthPost();
		healthPost.setPostId(postId);
		healthPost.setPostName(postName);
		healthPost.setKebele(kebele);
		return healthPost;
	}


	public String getKebele() {
		return kebele;
	}

	public void setKebele(String kebele) {
		this.kebele = kebele;
	}
	
}
