package contentmanager;

import java.io.File;
import dto.Content;

public class PathManager {
	private static final String path = "C:\\Users\\robinjoon\\Desktop\\photocloud\\devices";
	public String getSavePath(Content content) {
		String device = content.getDevice();
		String album = content.getAlbum();
		return path+File.separator+device+File.separator+album;
	}
	public boolean newPath(String path) {
		File file = new File(path);
		if(!file.exists()) {
			return file.mkdirs();
		}
		return false;
	}
}
