package com.douzone.jblog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.exception.UserRegistrationException;
import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceTest {
	@Autowired
	private UserService realUserService;
	@Autowired
	private UserRepository realUserRepository;
	@InjectMocks
	private UserService userServiceWithMockRepositories;
	@Mock
	private UserRepository mockUserRepository;
	@Mock
	private BlogRepository mockBlogRepository;
	@Mock
	private CategoryRepository mockCategoryRepository;
	
	
	@Value("${policy.userid.valid-pattern}")
	private String validIdPattern;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userServiceWithMockRepositories.setValidPattern(validIdPattern);
	}
	
	@Test
	public void idValidationTest() {
		UserVo user = new UserVo();
		user.setId("assets");
		assertFalse(userServiceWithMockRepositories.isValidPattern(user.getId()));
		user.setId("admin");
		assertFalse(userServiceWithMockRepositories.isValidPattern(user.getId()));
		
		user.setId("aaadsad123");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
		user.setId("admin1234");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
	}
	
	/**
	 * ????????? ??? ?????? ?????????(ex : assets, admin,..)?????? ?????? ??? ??????
	 */
	@Test(expected = UserRegistrationException.class)
	public void registerFailOnInvalidId() {
		UserVo user = new UserVo();
		user.setId("assets");
		assertFalse(userServiceWithMockRepositories.isValidPattern(user.getId()));
		
		
		doReturn(true).when(mockUserRepository).insert(anyObject());
		doReturn(true).when(mockBlogRepository).insert(anyObject());
		doReturn(true).when(mockCategoryRepository).insert(anyObject());
		
		assertTrue(mockUserRepository.insert(user));
		assertTrue(mockBlogRepository.insert(null));
		assertTrue(mockCategoryRepository.insert(null));
		
		userServiceWithMockRepositories.register(user);
	}
	/**
	 * id??? ?????????????????? mock repository?????? ?????? ??????????????? ???????????? ????????????????????? ???????????? ??? ??????????????? ??????
	 */
	@Test
	public void registerSuccessOnValidId() {
		UserVo user = new UserVo();
		user.setId("assetsd");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
		
		BlogVo blog = new BlogVo();
		CategoryVo category = new CategoryVo();
		
		doReturn(true).when(mockUserRepository).insert(anyObject());
		doReturn(true).when(mockBlogRepository).insert(anyObject());
		doReturn(true).when(mockCategoryRepository).insert(anyObject());
		
		assertTrue(mockUserRepository.insert(user));
		assertTrue(mockBlogRepository.insert(blog));
		assertTrue(mockCategoryRepository.insert(category));
		
		userServiceWithMockRepositories.register(user);
	}
		
	/**
	 * user record ?????? ??????
	 * (mock BlogRepository??? categoryRepository??? insert??? ??????????????? ??????)
	 * 
	 */
	@Test(expected = UserRegistrationException.class)
	public void registerFailOnAddUser() {
		
		doReturn(true).when(mockBlogRepository).insert(anyObject());
		doReturn(true).when(mockCategoryRepository).insert(anyObject());
		doReturn(null).when(mockUserRepository).insert(anyObject(),anyBoolean());
		UserVo user = new UserVo();
		user.setId("validId");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
		
		userServiceWithMockRepositories.register(user);
	}
	/**
	 * blog record ?????? ??????
	 * (mock userRepository??? categoryRepository??? insert??? ??????????????? ??????)
	 */
	@Test(expected = UserRegistrationException.class)
	public void registerFailOnAddBlog() {
		doReturn(false).when(mockBlogRepository).insert(anyObject());
		doReturn(true).when(mockCategoryRepository).insert(anyObject());
		doReturn(true).when(mockUserRepository).insert(anyObject());
		UserVo user = new UserVo();
		user.setId("validId");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
		userServiceWithMockRepositories.register(user);
	}
	/**
	 * category record ?????? ??????
	 */
	@Test(expected = UserRegistrationException.class)
	public void registerFailOnAddCategory() {
		doReturn(true).when(mockBlogRepository).insert(anyObject());
		doReturn(false).when(mockCategoryRepository).insert(anyObject());
		doReturn(true).when(mockUserRepository).insert(anyObject());
		UserVo user = new UserVo();
		user.setId("validId");
		assertTrue(userServiceWithMockRepositories.isValidPattern(user.getId()));
		userServiceWithMockRepositories.register(user);
	}
	
	/**
	 * ?????? ??????(mock repository??????)
	 */
	@Test
	public void addUserSuccessWithMockRepository() {
		doReturn(true).when(mockBlogRepository).insert(anyObject());
		doReturn(true).when(mockCategoryRepository).insert(anyObject());
		doReturn(true).when(mockUserRepository).insert(anyObject());
		UserVo userVo = new UserVo();
		userVo.setId("aaaaaa1");
		userVo.setName("asasdfd");
		userVo.setPassword("12341234");
		assertTrue(userServiceWithMockRepositories.register(userVo));
	}
	/**
	 * ????????????(real repository??????)
	 */
	@Transactional
	@Test
	public void addUserSuccess() {
		long beforeCnt = realUserRepository.getCnt();
		UserVo userVo = new UserVo();
		userVo.setId("aaaaaa1");
		userVo.setName("asasdfd");
		userVo.setPassword("12341234");
		assertTrue(realUserService.register(userVo));
		long afterCnt = realUserRepository.getCnt();
		assertEquals(beforeCnt + 1, afterCnt);
	}
}
