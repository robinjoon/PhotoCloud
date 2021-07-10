package dto;

public class Content {
	String device;
	String album;
	String originalFileName;
	String systemFileName;
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
	public String getAlbum() {
		return album;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public String getSystemFileName() {
		return systemFileName;
	}
	
}
