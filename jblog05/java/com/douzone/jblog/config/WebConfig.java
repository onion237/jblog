package com.douzone.jblog.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.douzone.jblog.mvc.util.AuthUserArgumentResolver;
import com.douzone.jblog.security.AuthInterceptor;
import com.douzone.jblog.security.LoginInterceptor;
import com.douzone.jblog.security.LogoutInterceptor;
import com.douzone.jblog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages = { "com.douzone.jblog.controller", "com.douzone.jblog.advice.contoller" })
@PropertySource("classpath:jblog.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private UserService userService;
	@Autowired
	private Environment environment;

	/**
	 * argument resolver
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new AuthUserArgumentResolver());
	}

	/**
	 * message converter
	 */
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
		messageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "html", Charset.forName("UTF-8"))));
		return messageConverter;
	}

	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().indentOutput(true)
				.dateFormat(new SimpleDateFormat("yyyy-mm-dd")) //
				.build();

		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
		messageConverter
				.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8"))));
		return messageConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

	/**
	 * resource handler
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.setOrder(214748364).addResourceHandler("/assets/upload-images/**")
				.addResourceLocations("file:/upload-images/");

		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}

	/**
	 * interceptor
	 */
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor(userService);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
			.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/login");
		registry
			.addInterceptor(new LogoutInterceptor())
			.addPathPatterns("/user/logout");
		
		registry
			.addInterceptor(new AuthInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(
					"/user/login",
					"/api/user/checkid",
					"/user/logout", 
					"/assets/**");
	}
	
	/**
	 * multipart resolver
	 */
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(environment.getProperty("fileupload.maxUploadSize", Integer.class));
		multipartResolver.setMaxInMemorySize(environment.getProperty("fileupload.maxInMemorySize", Integer.class));
		multipartResolver.setDefaultEncoding(environment.getProperty("fileupload.defaultEncoding"));
		
		return multipartResolver;
	}
	
	/**
	 * messageSource
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/messages_ko");
		messageSource.setDefaultEncoding("UTF-8");
		
		
		return messageSource;
	}

	/**
	 * view resolver
	 */

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(new InternalResourceViewResolver("/WEB-INF/views/", ".jsp"));
	}

}
