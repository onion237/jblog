package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.douzone.jblog.exception.UserRegistrationException;
import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
@PropertySource("classpath:jblog.properties")
public class UserService {
	private final UserRepository userRepository;
	private final BlogRepository blogRepository;
	private final CategoryRepository categoryRepository;
	
	@Value("${policy.userid.valid-pattern}")
	private String validPattern;
	@Value("${url.default.logo}")
	private String defaultLogoUrl;
	
	public void setValidPattern(String validPattern) {
		this.validPattern = validPattern;
	}
	
	public UserService(UserRepository userRepository, 
			BlogRepository blogRepository,
			CategoryRepository categoryRepository) {
		super();
		this.userRepository = userRepository;
		this.blogRepository = blogRepository;
		this.categoryRepository = categoryRepository;
		System.out.println("constructor");
	}

	public UserVo getUser(String id, String password) {
		return userRepository.findByIdAndPassword(id, password);
	}

	@Transactional
	public boolean register(UserVo user) throws UserRegistrationException {
		// validation
		if(!isValidPattern(user.getId())) 
			throw new UserRegistrationException("사용할 수 없는 아이디입니다.");
		
		// 유저 생성
		Long userNo = userRepository.insert(user, true);
		System.out.println(userNo);
		if(userNo == null) throw new UserRegistrationException("user 정보 등록 실패");
		
		user.setNo(userNo);
		
		// 블로그 생성
		BlogVo blog = new BlogVo();
		blog.setNo(user.getNo());
		blog.setTitle(user.getName() + "님의 블로그");
		blog.setLogo(defaultLogoUrl);
		if(!blogRepository.insert(blog)) throw new UserRegistrationException("default blog 생성 실패");
		
		// 카테고리 생성
		CategoryVo category = new CategoryVo();
		category.setBlogNo(blog.getNo());
		category.setName("기타");
		category.setDefault(true);
		if(!categoryRepository.insert(category)) throw new UserRegistrationException("default category 생성 실패");
		
		return true;
		
	}
	
	public boolean isValidPattern(String id) {
		return validPattern != null && id != null && id.matches(validPattern);
	}

	public boolean isDuplicatedId(String userId) {
		return userRepository.findById(userId) != null;
	}

	public boolean isAvailableId(String userId) {
		return isValidPattern(userId) && userRepository.findById(userId) == null;
	}


}
