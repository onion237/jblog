package com.douzone.jblog.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.mvc.util.ApiResult;
import com.douzone.jblog.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
	private final UserService userService;
	
	public UserApiController(UserService userService) {
		super();
		this.userService = userService;
		System.out.println("==========================================================");
	}

	@PostMapping("/checkid")
	public ApiResult idCheck(@RequestParam("id") String userId) {
		System.out.println("asdasdasdasd");
		if(!userService.isAvailableId(userId)) {
			return ApiResult.fail("duplicated id");
		}
		return ApiResult.success(true);
	}
}
