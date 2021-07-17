package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		pathManager.newPath(path);
		content = saver.save(content,path);
		dao.insert(content);
		model.addAttribute("message",content.getOriginalFileName()+" 업로드 성공");
		return "upload";
	}
	@GetMapping("/{device}/{album}/{id}")
	private String get(@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("id")int id, Model model) {
		Content content = dao.get(device+"_"+album, id);
		String path = pathManager.getSavePath(content);
		String src = path+File.separator+content.getSystemFileName();
		File file = loader.load(src);
		src = loader.encoder(file);
		model.addAttribute("src",src);
		model.addAttribute("name", content.getOriginalFileName());
		if(content.getTimestamp()!=null)
		model.addAttribute("time", content.getTimestamp().toLocalDateTime());
		model.addAttribute("locate", content.getLocate());
		model.addAttribute("device",content.getDevice());
		model.addAttribute("album", content.getAlbum());
		return "imageView";
	}
	@GetMapping("/{device}/{album}/list/{page}")
	private String getMany(@PathVariable("device")String device,@PathVariable("album")String album,@PathVariable("page")int page, Model model) {
		String tableName = device+"_"+album;
		List<Content> contents = dao.getPage(tableName, page);
		for(Content content : contents) {
			String path = pathManager.getSavePath(content);
			String src = path+File.separator+content.getSystemFileName();
			File file = loader.load(src);
			src = loader.encoder(file);
			content.setSystemFileName(src);
		}
		model.addAttribute("contentCount", dao.getContentCount(tableName));
		model.addAttribute("nowPage", page);
		model.addAttribute("contents", contents);
		return "imageListView";
	}
}
