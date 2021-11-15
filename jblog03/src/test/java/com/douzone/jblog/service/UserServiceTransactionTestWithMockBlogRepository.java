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
import com.douzone.test.jblog.config.AppContextWithMockBlogRepositoryConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppContextWithMockBlogRepositoryConfig.class}) 
public class UserServiceTransactionTestWithMockBlogRepository {
	@Autowired
	UserService userService;
	@Autowired
	BlogRepository blogRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	@Test
	public void checkBlogRepositoryBeanInSpringContainerIsMock() {
		assertTrue(new MockUtil().isMock(blogRepository));
	}
	@Test
	public void checkCategoryRepositoryBeanInSpringContainerIsNotMock() {
		System.out.println(categoryRepository);
		assertFalse(new MockUtil().isMock(categoryRepository));
	}
	@Test
	public void checkUserRepositoryBeanInSpringContainerIsNotMock() {
		System.out.println(userRepository);
		assertFalse(new MockUtil().isMock(userRepository));
	}
	
	@Test(expected = UserRegistrationException.class)
	public void userRegisterThrowUserRegistrationExceptionOnBlogInsert() {
		doReturn(false).when(blogRepository).insert(anyObject());
		UserVo user = new UserVo();
		user.setId("asdasdasdasdasd");
		user.setName("fffffffwwwwwa");
		user.setPassword("12341234");
		
		userService.register(user);
	}
	@Test
	public void checkUserInsertRollbackedAfterTransactionFail() {
		long beforeCnt = userRepository.getCnt();
		doReturn(false).when(blogRepository).insert(anyObject());
		
		System.out.println(blogRepository.insert(null));
		UserVo user = new UserVo();
		user.setId("asdasdasdasdad");
		user.setName("aaaaaaaaaaa");
		user.setPassword("12341234");
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
