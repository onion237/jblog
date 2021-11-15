package com.douzone.jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}
	public boolean addCategory(CategoryVo categoryVo) {
		return false;
	}
	public boolean modify(CategoryVo categoryVo) {
		return false;
	}
	public boolean delete(Long categoryNo) {
		return false;
	}
	
	public CategoryVo get(Long no) {
		return null;
	}
	
	public List<CategoryVo> getList(String blogId){
		return categoryRepository.findByBlogId(blogId);
	}
}
