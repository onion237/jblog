package com.douzone.jblog.controller.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.annotation.Auth;
import com.douzone.jblog.annotation.AuthUser;
import com.douzone.jblog.mvc.util.ApiResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PageInfo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@RestController
@RequestMapping("/api/blog/{blogId}")
public class BlogApiController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final PostService postService;


	public BlogApiController(BlogService blogService, CategoryService categoryService, PostService postService) {
		super();
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
	}

	@GetMapping("/categories")
	public ApiResult getCategoryList(@PathVariable("blogId") String blogId){
		return ApiResult.success(categoryService.getList(blogId));
	}
	
	@Auth
	@PostMapping("/category")
	public ApiResult insertCategory(@RequestBody CategoryVo category, @AuthUser UserVo authUser) {
		category.setBlogNo(authUser.getNo());
		categoryService.addCategory(category);
		category.setPostCount(0L);
		return ApiResult.success(category);
	}
	@Auth
	@DeleteMapping("/category/{no}")
	public ApiResult insertCategory(@PathVariable Long no) {
		boolean deleted = categoryService.delete(no);
		return deleted? ApiResult.success(deleted): ApiResult.fail("삭제 실패");
	}

	@GetMapping("/posts")
	public ApiResult getPostList(
			@PathVariable("blogId") String blogId, 
			@RequestParam(value="category_no", required = false) Long categoryNo,
			PageInfo pageInfo){
			
		PostVo post = new PostVo();
		post.setUserId(blogId);
		post.setCategoryNo(categoryNo);
		
		return ApiResult.success(postService.getList(post, pageInfo));
	}
	
	@Auth
	@PostMapping("/posts")
	public ApiResult post(@RequestBody PostVo post) {
		return ApiResult.success(postService.add(post));
	}
	
	@Auth
	@PostMapping
	public ApiResult updateBasicInfo(BlogVo blog, @RequestPart("file") MultipartFile file) {
		try {
			blogService.modify(blog, file.getBytes(), file.getOriginalFilename());
			return ApiResult.success(blog);
		} catch (IOException e) {
			e.printStackTrace();
			return ApiResult.fail("file 정보를 읽던 중 error가 발생했습니다");
		}
	}
}
