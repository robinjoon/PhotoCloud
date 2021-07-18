package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import intercepter.AuthIntercepter;


@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	private final int MAX_SIZE = 1024 * 1024 * 1024;
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(MAX_SIZE); // 10MB
		multipartResolver.setMaxUploadSizePerFile(MAX_SIZE); // 10MB
		multipartResolver.setMaxInMemorySize(0);
		return multipartResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(authIntercepter()).addPathPatterns("/**");
	}
	@Bean
	public AuthIntercepter authIntercepter() {
		return new AuthIntercepter();
	}
}
