package contentmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class ContentLoader {
	public File load(String path) {
		return new File(path);
	}
	public String encoder(File file) {
		String base64Image = "data:image/jpg;base64,";
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");
		String extension = fileName.substring(pos+1);
		base64Image = base64Image.replace("jpg", extension);
		try (FileInputStream imageInFile = new FileInputStream(file)) {
			// Reading a Image file from file system
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			base64Image = base64Image + Base64.getEncoder().encodeToString(imageData);
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
		return base64Image;
	}
}
