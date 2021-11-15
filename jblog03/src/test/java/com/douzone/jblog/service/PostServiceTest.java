package com.douzone.jblog.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PostServiceTest {
	@Autowired
	private CategoryRepository realCategoryRepository;
	@Autowired
	private BlogRepository realBlogRepository;
	@Autowired
	private UserRepository realUserRepository;
	@Autowired
	private PostRepository realPostRepository;
	
	@InjectMocks
	private PostService postService;
	@Mock
	private PostRepository postRepository;
	@Mock
	private CategoryRepository categoryRepository;
//	private PostVo samplePost;
//	private UserVo user;
//	
//	@Before
//	@Transactional
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		
//		user = new UserVo();
//		user.setId("test01");
//		user.setName("테스트유저");
//		user.setPassword("12341234");
//		Long userNo = realUserRepository.insert(user, true);
//		user.setNo(userNo);
//		
//		BlogVo blog = new BlogVo();
//		blog.setNo(user.getNo());
//		blog.setTitle("테스트블로그");
//		blog.setLogo("테스트로고경로");
//		realBlogRepository.insert(blog);
//		
//		CategoryVo category = new CategoryVo();
//		category.setBlogNo(blog.getNo());
//		category.setName("카테고리10");
//		category.setDesc("dhkdashflashdflkas");
//		Long categoryNo = realCategoryRepository.insert(category, true);
//		category.setNo(categoryNo);
//		
//		samplePost = new PostVo();
//		samplePost.setCategoryNo(category.getNo());
//		samplePost.setContents("테스트 컨텐츠");
//		samplePost.setTitle("테스트 타이틀");
//		
//		realPostRepository.insert(samplePost, true);
//		assertNotNull(samplePost.getNo());
//	}
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void postSuccess() {
		when(postRepository.insert(anyObject())).thenReturn(true);
		
		PostVo post = new PostVo();
		post.setTitle("testtestset");
		post.setContents("111111111111111111111111111111111111asdad");
		post.setCategoryNo(1L);
		
		boolean addResult = postService.add(post);
		assertTrue(addResult);
	}
	
	@Test
	public void authorized() {
		PostVo post = new PostVo();
		post.setUserNo(1L);
		post.setCategoryNo(1L);
		post.setNo(1L);
		when(categoryRepository.getUserNo(post.getCategoryNo())).thenReturn(post.getUserNo());
		assertTrue(postService.authorized(post));

		when(categoryRepository.getUserNo(post.getCategoryNo())).thenReturn(post.getUserNo() + 1);
		assertFalse(postService.authorized(post));

		UserVo user = new UserVo();
		user.setNo(1L);
		when(postRepository.getUserByPostNo(post.getNo())).thenReturn(user);
		post.setCategoryNo(null);
		assertTrue(postService.authorized(post));
		
		user.setNo(2L);
		when(postRepository.getUserByPostNo(post.getNo())).thenReturn(user);
		post.setCategoryNo(null);
		
		assertFalse(postService.authorized(post));
		
	}
	
//	@Test
//	@Transactional
//	public void authorized() {
//		samplePost.setUserNo(user.getNo());
//		assertTrue(postService.authorized(samplePost));
//		Long originCategory = samplePost.getCategoryNo();
//		samplePost.setCategoryNo(0L);
//		assertFalse(postService.authorized(samplePost));
//		samplePost.setCategoryNo(originCategory);
//		assertTrue(postService.authorized(samplePost));
//		samplePost.setUserNo(0L);
//		assertFalse(postService.authorized(samplePost));
//	}
	

}
