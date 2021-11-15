package com.douzone.jblog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.douzone.jblog.exception.UserRegistrationException;
import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;
import com.douzone.test.jblog.config.AppContextWithMockCategoryRepositoryConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppContextWithMockCategoryRepositoryConfig.class})
public class UserServiceTransactionTestWithMockCategoryRepository {
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	BlogRepository blogRepository;
	
	
	@Test
	public void checkCategoryRepositoryBeanInSpringContainerIsMock() {
		assertTrue(new MockUtil().isMock(categoryRepository));		
	}
	@Test
	public void checkUserRepositoryBeanInSpringContainerIsNotMock() {
		assertFalse(new MockUtil().isMock(userRepository));		
	}
	@Test
	public void checkBlogRepositoryBeanInSpringContainerIsNotMock() {
		assertFalse(new MockUtil().isMock(blogRepository));		
	}
	
	
	@Test(expected = UserRegistrationException.class)
	public void userRegisterThrowUserRegistrationExceptionOnCategoryInsert() {
		doReturn(false).when(categoryRepository).insert(anyObject());
		UserVo user = new UserVo();
		user.setId("asdasdasdasdasd");
		user.setName("fffffffwwwwwa");
		user.setPassword("12341234");
		
		userService.register(user);
	}
	
	@Test
	public void checkUserInsertRollbackedAfterTransactionFail() {
		long beforeCnt = userRepository.getCnt();
		doReturn(false).when(categoryRepository).insert(anyObject());
		
		System.out.println(categoryRepository.insert(null));
		UserVo user = new UserVo();
		user.setId("asdasdasdasdad");
		user.setName("aaaaaaaaaaa");
		user.setPassword("12341234");
		System.out.println(blogRepository);
		try {
		userService.register(user);			
		}catch (UserRegistrationException e) {
			long afterCnt = userRepository.getCnt();
			
			assertEquals(beforeCnt, afterCnt);
			return;
		}
		
		throw new UserRegistrationException();
	}
}
