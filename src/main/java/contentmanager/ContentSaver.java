package contentmanager;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import dto.Content;

public class ContentSaver {
	public Content save(Content content, String path) throws IOException {
		MultipartFile mfile = content.getFile();
		File file = new File(path+File.separator+mfile.getOriginalFilename());
		int i=0;
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");
		String extension = fileName.substring(pos+1);
		while(file.exists()) {
			i++;
			String pureFilename = fileName.substring(0, pos);
			file = new File(path+File.separator+pureFilename+"("+i+")."+extension);
		}
		System.err.println(file.getAbsolutePath());
		try {
			mfile.transferTo(file);
			content = new Content(content.getDevice(), content.getAlbum(), content.getOriginalFileName(), file.getName());
			return content;
		}catch(Exception e) {
			return null;
		}
		
	}
}
