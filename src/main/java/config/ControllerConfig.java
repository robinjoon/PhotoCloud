package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.APIController;
import controller.ContentController;
import controller.DeviceController;




@Configuration
public class ControllerConfig {

	@Bean
	public ContentController accountBookController() {
		return new ContentController();
	}
	@Bean
	public DeviceController deviceController() {
		return new DeviceController();
	}
	@Bean 
	public APIController apiController() {
		return new APIController();
	}
}
