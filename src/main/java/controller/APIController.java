package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dao.*;
import dto.Content;
import dto.ErrorResponse;

@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private DeviceDao deviceDao;
	
	@GetMapping("/{device}/{album}/images/{contentID}")
	private ResponseEntity<Object> getImage(@PathVariable("device")String device, @PathVariable("album")String album, @PathVariable("contentID")int contentID) {
		String tableName = device+"_"+album;
		Content content = contentDao.get(tableName, contentID);
		if(content == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Invalid contentID."));
		}else
			return ResponseEntity.status(HttpStatus.OK).body(content);
	}
	@GetMapping("/{device}/{album}/images/{startID}/{endID}")
	private ResponseEntity<Object> getImages(@PathVariable("device")String device, @PathVariable("album")String album, @PathVariable("startID")int startID, @PathVariable("endID")int endID) {
		String tableName = device+"_"+album;
		List<Content> contents = contentDao.get(tableName, startID,endID);
		if(contents==null || contents.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Invalid startID or endID."));
		}else
			return ResponseEntity.status(HttpStatus.OK).body(contents);
	}
	@GetMapping("/devices/list")
	private ResponseEntity<Object> getDevices(){
		List<String> devices = deviceDao.getDeviceList();
		if(devices==null||devices.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("None of the devices."));
		}else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(devices);
	}
	@GetMapping("/{device}/albums/list")
	private ResponseEntity<Object> getAlbums(@PathVariable("device")String device){
		List<String> albums = deviceDao.getalbumList(device);
		if(albums==null||albums.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Invalid device."));
		}else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(albums);
	}
}
