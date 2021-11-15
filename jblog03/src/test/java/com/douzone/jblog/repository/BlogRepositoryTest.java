package com.douzone.jblog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BlogRepositoryTest {
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	private BlogVo sampleBlog;
	private UserVo sampleUser;
	
	@Before
	public void setUp() {
		sampleUser = new UserVo();
		sampleUser.setName("김또치");
		sampleUser.setId("doochi1234");
		sampleUser.setPassword("12341234");
		
		Long userNo = userRepository.insert(sampleUser, true);		
		sampleUser.setNo(userNo);
		
		sampleBlog = new BlogVo();
		sampleBlog.setNo(userNo);
		sampleBlog.setLogo("Spring Logo");
		sampleBlog.setTitle("Spring Blog");
		
		blogRepository.insert(sampleBlog);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void insetBlogWithNoUser() {
		BlogVo blogVo = new BlogVo();
		blogVo.setNo(0L);
		blogVo.setTitle("dddddd");
		blogVo.setLogo("dddddd");
		blogRepository.insert(blogVo);
	}
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void insetBlogWithNoTitle() {
		BlogVo blogVo = new BlogVo();
		blogVo.setNo(0L);
		blogVo.setLogo("dddddd");
		blogRepository.insert(blogVo);
	}
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void insetBlogWithNoLogo() {
		BlogVo blogVo = new BlogVo();
		blogVo.setNo(0L);
		blogVo.setTitle("dddddd");
		blogRepository.insert(blogVo);
	}
	
	
	@Test
	@Transactional
	public void insert() {
		UserVo user = new UserVo();
		user.setName("second user");
		user.setId("second Id");
		user.setPassword("second password");
		
		Long userNo = userRepository.insert(user, true);
		assertNotNull(userNo);
		user.setNo(userNo);
		
		
		BlogVo vo = new BlogVo();
		vo.setNo(user.getNo());
		vo.setLogo("second logo");
		vo.setTitle("second blog");
		
		boolean result = blogRepository.insert(vo);
		assertTrue(result);
		
		BlogVo findResult = blogRepository.findByNo(vo.getNo());
		assertNotNull(findResult);
		assertEquals(findResult.getNo(), vo.getNo());
		assertEquals(findResult.getTitle(), vo.getTitle());
		assertEquals(findResult.getLogo(), vo.getLogo());
	}
	
	@Test
	@Transactional
	public void findByNo() {
		BlogVo result = blogRepository.findByNo(sampleBlog.getNo());
		assertNotNull(result);
		assertEquals(result.getNo(), sampleBlog.getNo());
		assertEquals(result.getTitle(), sampleBlog.getTitle());
		assertEquals(result.getLogo(), sampleBlog.getLogo());
	}
	
	@Test
	@Transactional
	public void findByUserId() {
		BlogVo result = blogRepository.findByUserId(sampleUser.getId());
		assertNotNull(result);
		assertEquals(result.getNo(), sampleBlog.getNo());
		assertEquals(result.getTitle(), sampleBlog.getTitle());
		assertEquals(result.getLogo(), sampleBlog.getLogo());
	}
	
}
