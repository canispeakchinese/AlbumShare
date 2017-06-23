package domain;

import java.io.Serializable;

public class Photo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private int albumId;
	private int photoId;
	private String photoName;
	private String description;
	private String comment;
	
	public Photo() {
		
	}
	
	public Photo(int userId, int albumId, int photoId,
			String photoName, String description, String comment) {
		this.userId = userId;
		this.albumId = albumId;
		this.photoId = photoId;
		this.photoName = photoName;
		this.description = description;
		this.comment = comment;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getAlbumId() {
		return albumId;
	}
	
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	
	public int getPhotoId() {
		return photoId;
	}
	
	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}
	
	public String getPhotoName() {
		return photoName;
	}
	
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
