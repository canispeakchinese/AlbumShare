package domain;

import java.io.Serializable;

public class AlbumInfor implements Serializable {
	private static final long serialVersionUID = 1L;
	private String albumName;
	private String albumCover;
	private int photoNum;
	
	public AlbumInfor() {
		
	}
	
	public AlbumInfor(String albumName, String albumCover, int photoNum) {
		this.albumName = albumName;
		this.albumCover = albumCover;
		this.photoNum = photoNum;
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
