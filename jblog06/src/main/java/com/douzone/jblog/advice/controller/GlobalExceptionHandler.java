package com.douzone.jblog.advice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.jblog.mvc.util.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(Exception.class)
	public void handlerException(
			HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
		
		// 1. 로깅
		StringWriter errors = new StringWriter();
		
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		// 2. 요청 구분
		// 만약, JSON 요청인 경우 request header의 Accept에 application/json
		// 만약, html 요청인 경우 request header의 Accept에 text/html
		//일단, 컨텐츠 협상 고려 x
		if(request.getHeader("accept").contains("application/json")) {
			// 3. Json Sorry
			response.setContentType("application/json; charset=utf-8");
			response
				.getWriter()
				.println(new ObjectMapper().writeValueAsString(ApiResult.fail(errors.toString())));
			
		}else {
			// 3. Html Sorry
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
			
		}
	}
}
