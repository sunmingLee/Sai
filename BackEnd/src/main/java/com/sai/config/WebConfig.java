package com.sai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${resources.location}")
	private String resourcesLocation;
	@Value("${resources.uri_path:}")
	private String resourcesUriPath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").exposedHeaders("X-AUTH-TOKEN")
//                .allowCredentials(true)
				.allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
//                .allowedOrigins("http://localhost:3000");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(resourcesUriPath + "/**").addResourceLocations("file://" + resourcesLocation);
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
