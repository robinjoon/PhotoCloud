package dto;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class Content {
	String device;
	String album;
	int contentId;
	String originalFileName;
	String systemFileName;
	MultipartFile file;
	Timestamp timestamp;
	String locate;
	public Content(String device, String album, String originalFileName, String systemFileName) {
		super();
		this.device = device;
		this.album = album;
		this.originalFileName = originalFileName;
		this.systemFileName = systemFileName;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getSystemFileName() {
		return systemFileName;
	}
	public void setSystemFileName(String systemFileName) {
		this.systemFileName = systemFileName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
}
