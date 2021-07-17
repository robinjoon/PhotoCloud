package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.ContentController;




@Configuration
public class ControllerConfig {

	@Bean
	public ContentController accountBookController() {
		return new ContentController();
	}

}
