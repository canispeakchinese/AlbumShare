package domain;

import java.io.Serializable;

public class PhotoInfor implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String photoName;
	private String description;
	private boolean isOpen;
	private String comment;
	
	public PhotoInfor() {
		
	}
	
	public PhotoInfor(int id, String photoName, String description,
			boolean isOpen, String comment) {
		this.id = id;
		this.photoName = photoName;
		this.description = description;
		this.isOpen = isOpen;
		this.comment = comment;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public boolean getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
}
