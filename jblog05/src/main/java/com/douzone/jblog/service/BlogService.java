package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PageInfo;

@Service
@PropertySource("classpath:jblog.properties")
public class BlogService {
	private final BlogRepository blogRepository;
	private final CategoryRepository categoryRepository;
	private final PostRepository postRepository;
	private final FileUploadService fileUploadService;
	@Value("${fileupload.logo-path}")
	private String LOGO_PATH;
	//////////////////////////////////////////////////////////////////////////////
	public BlogService(BlogRepository blogRepository, CategoryRepository categoryRepository,
			PostRepository postRepository, FileUploadService fileUploadService) {
		super();
		this.blogRepository = blogRepository;
		this.categoryRepository = categoryRepository;
		this.postRepository = postRepository;
		this.fileUploadService = fileUploadService;
	}

	public boolean modify(BlogVo blog, byte[] fileData, String originalFilename) {
		String url = fileUploadService.restore(fileData, originalFilename, LOGO_PATH);
		blog.setLogo(url);
		System.out.println(url + " ----------------------------------");
		return blogRepository.update(blog);
	}
	
	public BlogVo get(String userId) {
		return blogRepository.findByUserId(userId);
	}

	
	
	public List<BlogVo> getList(BlogVo blog, PageInfo pageInfo){
		return blogRepository.findAll(blog, pageInfo);
	}


}
