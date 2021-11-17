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
		return categoryRepository.insert(categoryVo);
	}
	public boolean modify(CategoryVo categoryVo) {
		return categoryRepository.update(categoryVo);
	}
	public boolean delete(Long categoryNo) {
		return categoryRepository.delete(categoryNo);
	}
	
	public CategoryVo get(Long no) {
		return null;
	}
	
	public List<CategoryVo> getList(String blogId){
		return categoryRepository.findByBlogId(blogId);
	}
}
