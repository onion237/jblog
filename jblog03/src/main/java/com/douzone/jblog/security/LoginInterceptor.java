package com.douzone.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	private final UserService userService;

	public LoginInterceptor(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equals("GET")) {
			System.out.println(getClass().getSimpleName() + ".login(GET)");
			return true;
		}
		
		System.out.println(getClass().getSimpleName() + ".login(POST)");
		
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo user = new UserVo();
		
		UserVo authUser = userService.getUser(id, password);
		if(authUser == null) {
			request.setAttribute("result", "fail");
			request.setAttribute("userVo", user);
			
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
		}else {
			request.getSession().setAttribute("authUser", authUser);
			response.sendRedirect(request.getContextPath());
		}
		
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
