package com.douzone.jblog.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.vo.UserVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	
	private UserVo sampleVo;
	@Before
	public void setUp() {
		sampleVo = new UserVo();
		sampleVo.setName("김또치");
		sampleVo.setId("doochi1234");
		sampleVo.setPassword("12341234");
		
		Long no = userRepository.insert(sampleVo, true);		
		sampleVo.setNo(no);
	}
	
	@Test
	@Transactional
	public void findById() {
		UserVo result = userRepository.findById(sampleVo.getId());
		assertNotNull(result);
		assertEquals(result.getNo(), sampleVo.getNo());
		assertEquals(result.getId(), sampleVo.getId());
		assertEquals(result.getName(), sampleVo.getName());
	}
	
	@Test
	@Transactional
	public void findByNo() {
		UserVo result = userRepository.findByNo(sampleVo.getNo());
		assertNotNull(result);
		assertEquals(result.getNo(), sampleVo.getNo());
		assertEquals(result.getId(), sampleVo.getId());
		assertEquals(result.getName(), sampleVo.getName());
	}
	@Test
	@Transactional
	public void findByIdAndPassword() {
		UserVo result = userRepository.findByIdAndPassword(sampleVo.getId(), sampleVo.getPassword());
		assertNotNull(result);
		assertEquals(result.getNo(), sampleVo.getNo());
		assertEquals(result.getId(), sampleVo.getId());
		assertEquals(result.getName(), sampleVo.getName());
	}
	
	@Test
	@Transactional
	public void insert() {
		UserVo vo = new UserVo();
		vo.setName("김둘리");
		vo.setId("dooly1234");
		vo.setPassword("12341234");
		
		Long no = userRepository.insert(vo, true);
		assertNotNull(no);
		UserVo result = userRepository.findByNo(no);
		assertNotNull(result);
		
		assertEquals(no, result.getNo());
		assertEquals(vo.getId(), result.getId());
		assertEquals(vo.getName(), result.getName());
	}
	
	@Test
	@Transactional
	public void delete() {
		UserVo result = userRepository.findById(sampleVo.getId());
		assertNotNull(result);
		assertEquals(result.getName(), sampleVo.getName());
		assertEquals(result.getId(), sampleVo.getId());
		
		boolean deleteResult = userRepository.delete(result.getNo());
		assertTrue(deleteResult);
		
		result = userRepository.findById(sampleVo.getId());
		assertNull(result);		
	}
	
	@Test
	@Transactional
	public void update() {
		UserVo result = userRepository.findById(sampleVo.getId());
		assertNotNull(result);
		assertEquals(result.getName(), sampleVo.getName());
		
		UserVo updateVo = new UserVo();
		updateVo.setNo(result.getNo());
		updateVo.setName("고길동");
		
		boolean updateResult = userRepository.update(updateVo);
		assertTrue(updateResult);
		result = userRepository.findById(sampleVo.getId());
		assertNotNull(result);
		
		assertNotEquals(result.getName(), sampleVo.getName());
		assertEquals(result.getName(), updateVo.getName());
	}
	
}
