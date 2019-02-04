package com.dkarakaya.sample.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Post {

	private int id;
	private String imageUrl;
	private String message;
	private int likeCount;
	private Timestamp creationDate;
	private Timestamp lastModifiedDate;
	private List<Link> links = new ArrayList<>();

	public Post() {

	}

	public Post(int id, String imageUrl, String message, int likeCount, Timestamp createdDate,
			Timestamp lastModifiedDate) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.message = message;
		this.likeCount = likeCount;
		this.creationDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}

}
