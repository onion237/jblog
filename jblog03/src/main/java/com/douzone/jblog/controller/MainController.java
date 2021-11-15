package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@Autowired
	ApplicationContext ctx;
	public MainController() {
		super();
	}
	@GetMapping("/")
	public String main() {
		return "main/index";
	}
//	@GetMapping({"/assets/{asd}/{name}", "/assets/{asdasd}/{name}/{aaa}"})
//	@ResponseBody
//	public String test() {
//		System.out.println(ctx.getBean(RequestMappingHandlerMapping.class).getOrder());
//		ctx.getBeansOfType(SimpleUrlHandlerMapping.class).forEach((key,val)->{
//			System.out.println(val.getOrder());
//			val.getUrlMap().forEach((key1,val1) -> {
//				System.out.println(key1 + " : " + val1);
//			});
//		});
//	
//		System.out.println("got it");
//		return "test";
//	}
}
