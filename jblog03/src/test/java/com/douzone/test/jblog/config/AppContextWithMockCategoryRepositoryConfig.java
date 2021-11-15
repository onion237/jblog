package com.douzone.test.jblog.config;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;

import com.douzone.jblog.repository.CategoryRepository;

@Configuration
@ImportResource("classpath:applicationContext.xml")
public class AppContextWithMockCategoryRepositoryConfig {
	@Bean
	@Primary
	public CategoryRepository categoryRepository() {
		return mock(CategoryRepository.class);
	}
}
