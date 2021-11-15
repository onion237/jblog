package com.douzone.jblog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PageInfo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final CategoryRepository categoryRepository;
	
	public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
		super();
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
	}
	
	public boolean add(PostVo post) {
		return postRepository.insert(post);
	}
	
	public boolean modify(PostVo post) {
		return postRepository.update(post);
	}
	
	public boolean delete(Long postNo) {
		return postRepository.delete(postNo);
	}
	
	public PostVo get(Long postNo) {
		return postRepository.findByNo(postNo);
	}
	
	public List<PostVo> getList(PostVo post, PageInfo pageInfo){
		pageInfo.setTotal((long)(Math.ceil(
										(double)postRepository.getCntOfSearchResult(post) 
										/ pageInfo.getResultPerPage()
										)
								));
		
		long begin =  pageInfo.getCur() - (pageInfo.getPageRange() - 1) / 2;
		pageInfo.setBegin(begin > 0 ? begin : 1);
		
		long end = begin + pageInfo.getPageRange() - 1;
		pageInfo.setEnd(end <= pageInfo.getTotal() ? end : pageInfo.getTotal());
		
		pageInfo.setNext(pageInfo.getCur() + 1 <= pageInfo.getTotal() ? pageInfo.getCur() + 1 : -1);
		pageInfo.setPrev(pageInfo.getCur() - 1 > 0 ? pageInfo.getCur() - 1 : -1);
		
		pageInfo.setOffset((pageInfo.getCur() - 1) * pageInfo.getResultPerPage());
		
		
		return postRepository.findAll(post, pageInfo);
	}
	
	public boolean authorized(PostVo postVo) {
		if(postVo == null || postVo.getUserNo() == null) return false;
		
		if(postVo.getCategoryNo() != null)
			return postVo.getUserNo() == categoryRepository.getUserNo(postVo.getCategoryNo());
		
		UserVo user = postRepository.getUserByPostNo(postVo.getNo());
		if(user == null) return false;
		return postVo.getUserNo() == user.getNo();
	}

	public PostVo getLatestPostInCategory(Long categoryNo) {
		// TODO Auto-generated method stub
		return postRepository.getLatest(categoryNo);
	}
}