package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import contentmanager.*;




@Configuration
public class ContentManagerConfig {

	@Bean
	public ContentLoader contentLoader() {
		return new ContentLoader();
	}
	@Bean 
	public ContentSaver contentSaver() {
		return new ContentSaver();
	}
	@Bean
	public PathManager pathManager() {
		return new PathManager();
	}
	@Bean
	public ThumbnailBuilder thumbnailBuilder() {
		return new ThumbnailBuilder();
	}
}
