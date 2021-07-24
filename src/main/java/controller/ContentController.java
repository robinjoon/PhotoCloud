package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import contentmanager.*;
import dao.ContentDao;
import dto.Content;

@Controller
@RequestMapping("/contents")
public class ContentController {
	@Autowired
	private ContentDao dao;
	@Autowired
	private ContentLoader loader;
	@Autowired
	private ContentSaver saver;
	@Autowired
	private PathManager pathManager;
	@Autowired
	private ThumbnailBuilder thumbnailBuilder;
	@PostMapping("/upload")
	private String save(@ModelAttribute Content content, Model model) throws IllegalStateException, IOException {
		String path = pathManager.getSavePath(content);
		String thumbnailPath = pathManager.getThumnailSavePath(content);
		pathManager.newPath(path);
		pathManager.newPath(thumbnailPath);
		content = saver.save(content,path);
		content.setUrl("/contents/"+content.getDevice()+"/"+content.getAlbum()+"/");
		content.setThumbNailUrl("/contents/"+content.getDevice()+"/"+content.getAlbum()+"/");
		try {
			thumbnailBuilder.makeThumbnail(path+File.separator+content.getSystemFileName(), thumbnailPath+File.separator+content.getSystemFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dao.insert(content);
		model.addAttribute("message",content.getOriginalFileName()+" 업로드 성공");
		return "upload";
	}
	@GetMapping("/{device}/{album}/{id}")
	private void getImage(HttpServletRequest req, HttpServletResponse res,@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("id")int id, Model model) throws IOException {
		ServletOutputStream imgout = res.getOutputStream();
		Content content = dao.get(device+"_"+album, id);
		String path = pathManager.getSavePath(content);
		String fileName = content.getSystemFileName();
		File file = loader.load(path+File.separator+fileName);
		FileInputStream input = new FileInputStream(file);
		int length;
		byte[] buffer = new byte[10];
		while( (length = input.read(buffer)) != -1) {
	        imgout.write(buffer, 0, length);
		}
		input.close();
	}
	@GetMapping("/{device}/{album}/{id}/thumbnail")
	private void getThumbnail(HttpServletRequest req, HttpServletResponse res,@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("id")int id, Model model) throws IOException {
		ServletOutputStream imgout = res.getOutputStream();
		Content content = dao.get(device+"_"+album, id);
		String path = pathManager.getThumnailSavePath(content);
		String fileName = content.getSystemFileName();
		File file = loader.load(path+File.separator+fileName);
		FileInputStream input = new FileInputStream(file);
		int length;
		byte[] buffer = new byte[10];
		while( (length = input.read(buffer)) != -1) {
	        imgout.write(buffer, 0, length);
		}
		input.close();
	}
	@GetMapping("/{device}/{album}/list/{page}")
	private String getMany(@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("page")int page, Model model) {
		String tableName = device+"_"+album;
		List<Content> contents = dao.getPage(tableName, page);
		List<String> srcs = new ArrayList<String>();
		for(Content content : contents) {
			srcs.add("/PhotoCloud/contents/"+device+"/"+album+"/"+content.getContentId()+"/thumbnail");
		}
		model.addAttribute("contentCount", dao.getContentCount(tableName));
		model.addAttribute("nowPage", page);
		model.addAttribute("contents", contents);
		model.addAttribute("srcs", srcs);
		return "imageListView";
	}
	@GetMapping("/{device}/{album}/{id}/info")
	private String getImageInfo(@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("id")int id, Model model) throws IOException {
		Content content = dao.get(device+"_"+album, id);
		model.addAttribute("name", content.getOriginalFileName());
		if(content.getTimestamp()!=null)
		model.addAttribute("time", content.getTimestamp().toLocalDateTime());
		model.addAttribute("locate", content.getLocate());
		model.addAttribute("device",content.getDevice());
		model.addAttribute("album", content.getAlbum());
		model.addAttribute("src","/PhotoCloud/contents/"+device+"/"+album+"/"+id);
		return "imageView";
	}
}
