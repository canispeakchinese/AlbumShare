package domain;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class UploadedPhoto implements Serializable {
	private static final long serialVersionUID = 1L;
	private MultipartFile file;
	private String photoName;
	private String description;
	private boolean isOpen;
	
	public UploadedPhoto() {
		
	}
	
	public UploadedPhoto(MultipartFile file, String photoName, String description,
			boolean isOpen) {
		this.file = file;
		this.photoName = photoName;
		this.description = description;
		this.isOpen = isOpen;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
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
}
