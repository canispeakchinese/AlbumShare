package domain;

import java.io.Serializable;

public class AlbumInfor implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String albumName;
	private String albumCover;
	private int photoNum;
	
	public AlbumInfor() {
		
	}
	
	public AlbumInfor(int id, String albumName, String albumCover, int photoNum) {
		this.id = id;
		this.albumName = albumName;
		this.albumCover = albumCover;
		this.photoNum = photoNum;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}
	
	public String getAlbumCover() {
		return albumCover;
	}
	
	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}
	
	public int getPhotoNum() {
		return photoNum;
	}
}
