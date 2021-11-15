package com.douzone.jblog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PostRepositoryTest {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	
	private PostVo samplePost;
	private UserVo user;
	
	@Before
	@Transactional
	public void setUp() {
		user = new UserVo();
		user.setId("test01");
		user.setName("테스트유저");
		user.setPassword("12341234");
		Long userNo = userRepository.insert(user, true);
		user.setNo(userNo);
		
		BlogVo blog = new BlogVo();
		blog.setNo(user.getNo());
		blog.setTitle("테스트블로그");
		blog.setLogo("테스트로고경로");
		blogRepository.insert(blog);
		
		CategoryVo category = new CategoryVo();
		category.setBlogNo(blog.getNo());
		category.setName("카테고리10");
		category.setDesc("dhkdashflashdflkas");
		Long categoryNo = categoryRepository.insert(category, true);
		category.setNo(categoryNo);
		
		samplePost = new PostVo();
		samplePost.setCategoryNo(category.getNo());
		samplePost.setContents("테스트 컨텐츠");
		samplePost.setTitle("테스트 타이틀");
		
		postRepository.insert(samplePost);
	}
	
	@Test
	@Transactional
	public void getUserNoByPostNo() {
		UserVo userByPostNo = postRepository.getUserByPostNo(samplePost.getNo());
		assertNotNull(userByPostNo);
		assertEquals(userByPostNo.getNo(), user.getNo());
		assertEquals(userByPostNo.getName(), user.getName());
		assertEquals(userByPostNo.getId(), user.getId());
	}
	@Test
	@Transactional
	public void findByNo() {
		PostVo findByNo = postRepository.findByNo(samplePost.getNo());
		assertNotNull(findByNo);
		assertEquals(findByNo.getNo(), samplePost.getNo());
		assertEquals(findByNo.getContents(), samplePost.getContents());
		assertEquals(findByNo.getTitle(), samplePost.getTitle());
	}
	
	@Test
	@Transactional
	public void findByCategoryNo() {
		PostVo post1 = new PostVo();
		post1.setTitle("111111111111111111");
		post1.setContents("11111111111111111111111");
		post1.setCategoryNo(samplePost.getCategoryNo());
		postRepository.insert(post1);
		PostVo post2 = new PostVo();
		post2.setCategoryNo(samplePost.getCategoryNo());
		post2.setTitle("22222222222222222222");
		post2.setContents("222222222222222222222222");
		postRepository.insert(post2);
		
		List<PostVo> list = postRepository.findAllByCategoryNo(samplePost.getCategoryNo());
		assertNotNull(list);
		assertEquals(list.size(), 3);
		
		PostVo post = list.get(0);
		assertEquals(post.getNo(), post2.getNo());
		assertEquals(post.getTitle(), post2.getTitle());
		assertEquals(post.getContents(), post2.getContents());
		
		post = list.get(1);
		assertEquals(post.getNo(), post1.getNo());
		assertEquals(post.getTitle(), post1.getTitle());
		assertEquals(post.getContents(), post1.getContents());
		
		post = list.get(2);
		assertEquals(post.getNo(), samplePost.getNo());
		assertEquals(post.getTitle(), samplePost.getTitle());
		assertEquals(post.getContents(), samplePost.getContents());
	}
	
	@Test
	@Transactional
	public void insert() {
		PostVo vo = new PostVo();
		vo.setCategoryNo(samplePost.getCategoryNo());
		vo.setTitle("sdfasfasfd");
		vo.setContents("ㅁㄴㄹㅇㅁㄴ어ㅣ럼ㄴㅇㄻㄴㅇ");
		
		Long postNo = postRepository.insert(vo, true);
		vo.setNo(postNo);
		
		assertNotNull(postNo);
		
		PostVo findByNo = postRepository.findByNo(postNo);
		assertNotNull(findByNo);
		assertEquals(findByNo.getNo(), vo.getNo());
		assertEquals(findByNo.getTitle(), vo.getTitle());
		assertEquals(findByNo.getContents(), vo.getContents());
		assertEquals(findByNo.getCategoryNo(), vo.getCategoryNo());	
	}
	
	@Test
	@Transactional
	public void update() {
		samplePost.setContents("contents after modified");
		samplePost.setTitle("title after modified");
		
		boolean result = postRepository.update(samplePost);
		assertTrue(result);
		PostVo updatedPost = postRepository.findByNo(samplePost.getNo());
		assertNotNull(updatedPost);
		assertEquals(samplePost.getNo(), updatedPost.getNo());
		assertEquals(samplePost.getTitle(), updatedPost.getTitle());
		assertEquals(samplePost.getContents(), updatedPost.getContents());
	}
	
	@Test
	@Transactional
	public void delete() {
		PostVo post = postRepository.findByNo(samplePost.getNo());
		assertNotNull(post);
		assertEquals(post.getNo(), samplePost.getNo());
		assertEquals(post.getTitle(), samplePost.getTitle());
		assertEquals(post.getCategoryNo(), samplePost.getCategoryNo());
		assertEquals(post.getContents(), samplePost.getContents());
		
		boolean result = postRepository.delete(samplePost.getNo());
		assertTrue(result);
		post = postRepository.findByNo(samplePost.getNo());
		assertNull(post);
	}

}
