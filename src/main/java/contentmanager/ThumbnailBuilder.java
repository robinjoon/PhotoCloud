package contentmanager;

import java.io.File;
import java.io.IOException;


public class ThumbnailBuilder {
	public boolean makeThumbnail(String originPath, String thumbnailPath) throws IOException, InterruptedException {
		String create = "/usr/bin/convert "+originPath+" -quality 10 "+thumbnailPath;
		System.out.println(create);
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(create);
		int end = proc.waitFor();
		if(end==0) {
			return true;
		}else {
			return false;
		}
	}
}
