package com.mycgv_jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NoticeController {
	
	
	 @RequestMapping(value = "/notice_list.do" , method = RequestMethod.GET) 
	 public String notice_list() { 
		 return "/notice/notice_list"; 
		 }
	 
	 
}
