package com.douzone.jblog.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	@Transactional
	public boolean delete(Long categoryNo) {
		CategoryVo vo = categoryRepository.findByNo(categoryNo);
		
		if(categoryRepository.getCategoryCntByBlogNo(vo.getBlogNo()) < 2){			
			return false;
		}
		
		boolean deleted = categoryRepository.delete(categoryNo);
		if(vo.isDefault()) {
			if(!categoryRepository.updateDefaultAny(vo)) return false;
		}
		
		return deleted;
	}
	
	public List<CategoryVo> getList(String blogId){
		return categoryRepository.findByBlogId(blogId);
	}
}
