package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.DeviceDao;

@Controller
@RequestMapping("/devices")
public class DeviceController {
	@Autowired
	private DeviceDao dao;
	
	@GetMapping("/list")
	private String getList(Model model) {
		List<String> devices = dao.getDeviceList();
		model.addAttribute("devices", devices);
		return "deviceListView";
	}
	@GetMapping("/{device}/albums/list")
	private String getAlbumList(@PathVariable("device")String device,Model model) {
		List<String> albums = dao.getalbumList(device);
		model.addAttribute("albums", albums);
		model.addAttribute("device", device);
		return "albumListView";
	}
}
