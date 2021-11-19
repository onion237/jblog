package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.annotation.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PageInfo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:${request-mapping.exclude-pattern}}")
public class BlogController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final PostService postService;
	


	public BlogController(BlogService blogService, CategoryService categoryService, PostService postService) {
		super();
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
	}

	@GetMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String test(
			Model model,
			PageInfo pageInfo,
			PostVo post,
			@PathVariable String id,
			@PathVariable(value="categoryNo", required = false) Optional<Long> categoryNo,
			@PathVariable(value="postNo", required = false) Optional<Long> postNo) {

		BlogVo blog = blogService.get(id);
		blog.setUserId(id);
		List<CategoryVo> categoryList = categoryService.getList(id);
		model.addAttribute("blog", blog);
		model.addAttribute("categoryList", categoryList);
		
		Optional<CategoryVo> defaultCategory = categoryList.stream().filter((category) -> category.isDefault()).findAny();
		
		post.setCategoryNo(categoryNo.orElse(defaultCategory.get().getNo()));
		model.addAttribute("postList", postService.getList(post, pageInfo));
		
		postNo.ifPresent((no) -> model.addAttribute("post",  postService.get(no)));
		postNo.orElseGet(() -> {
			model.addAttribute("post", postService.getLatestPostInCategory(categoryNo.orElse(defaultCategory.get().getNo())));
			return 1L;
		});
		
		return "blog/blog-main";
	}
	
	@Auth
	@GetMapping("/admin")
	public String admin(Model model, @PathVariable String id) {
		model.addAttribute("blog",blogService.get(id));
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@GetMapping("/admin/category")
	public String adminCategory(Model model, @PathVariable String id) {
		model.addAttribute("list", categoryService.getList(id));		
		return "blog/blog-admin-category";
	}
	
	@Auth
	@GetMapping("/admin/write")
	public String adminWrite(Model model, @PathVariable String id) {
		model.addAttribute("list", categoryService.getList(id));		
		return "blog/blog-admin-write";
	}
}
