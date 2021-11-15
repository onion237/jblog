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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CategoryRepositoryTet {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	
	private UserVo sampleUser;
	private BlogVo sampleBlog;
	private CategoryVo sampleCategory;
	@Before
	public void setUp() {
		sampleUser = new UserVo();
		sampleUser.setName("길동");
		sampleUser.setId("gggggggoooooo");
		sampleUser.setPassword("111111111");
		Long userNo = userRepository.insert(sampleUser, true);
		assertNotNull(userNo);
		sampleUser.setNo(userNo);
		
		sampleBlog = new BlogVo();
		sampleBlog.setNo(sampleUser.getNo());
		sampleBlog.setTitle("ggggglog");
		sampleBlog.setLogo("ggggggooooggoooooo");
		boolean insertResult = blogRepository.insert(sampleBlog);
		assertTrue(insertResult);

		
		sampleCategory = new CategoryVo();
		sampleCategory.setBlogNo(sampleBlog.getNo());
		sampleCategory.setDesc("sample blog");
		sampleCategory.setName("sample category");
		Long no = categoryRepository.insert(sampleCategory, true);
		assertNotNull(no);
		sampleCategory.setNo(no);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	public void insertCategoryWithNoBLog() {
		CategoryVo vo = new CategoryVo();
		vo.setBlogNo(0L);
		vo.setDesc("설명");
		vo.setName("카테고리1");
		categoryRepository.insert(vo);
	}
	
	@Test
	@Transactional
	public void insert() {
		UserVo user = new UserVo();
		user.setName("돌리");
		user.setId("doooooooooo");
		user.setPassword("111111111");
		Long userNo = userRepository.insert(user, true);
		assertNotNull(userNo);
		user.setNo(userNo);
		
		BlogVo blog = new BlogVo();
		blog.setNo(user.getNo());
		blog.setTitle("bbbblog");
		blog.setLogo("llloooooooggoooooo");
		boolean insertResult = blogRepository.insert(blog);
		assertTrue(insertResult);
		
		CategoryVo vo = new CategoryVo();
		vo.setBlogNo(blog.getNo());
		vo.setDesc("설명");
		vo.setName("카테고리1");
		boolean categoryInsertResult = categoryRepository.insert(vo);
		assertTrue(categoryInsertResult);
	}
	
	@Test
	@Transactional
	public void delete() {
		CategoryVo result = categoryRepository.findByNo(sampleCategory.getNo());
		assertNotNull(result);
		
		boolean deleteResult = categoryRepository.delete(sampleCategory.getNo());
		assertTrue(deleteResult);
		
		result = categoryRepository.findByNo(sampleCategory.getNo());
		assertNull(result);
	}
	
	@Test
	@Transactional
	public void findByNo() {
		CategoryVo result = categoryRepository.findByNo(sampleCategory.getNo());
		assertNotNull(result);
		assertEquals(result.getBlogNo(), sampleCategory.getBlogNo());
		assertEquals(result.getDesc(), sampleCategory.getDesc());
		assertEquals(result.getName(), sampleCategory.getName());
		assertEquals(result.getNo(), sampleCategory.getNo());
	}
	@Test
	@Transactional
	public void findByBlogNo() {
		List<CategoryVo> findByBlogNo = categoryRepository.findByBlogNo(sampleCategory.getBlogNo());
		assertNotNull(findByBlogNo);
		assertEquals(findByBlogNo.size(), 1);
		CategoryVo result = findByBlogNo.get(0);
		assertEquals(result.getBlogNo(), sampleCategory.getBlogNo());
		assertEquals(result.getDesc(), sampleCategory.getDesc());
		assertEquals(result.getName(), sampleCategory.getName());
		assertEquals(result.getNo(), sampleCategory.getNo());
	}
	
	@Test
	@Transactional
	public void getUserNo() {	
		Long userNo = categoryRepository.getUserNo(sampleCategory.getNo());
		assertNotNull(userNo);
		assertEquals(userNo, sampleUser.getNo());
		assertEquals(userNo, sampleBlog.getNo());
	}

}
