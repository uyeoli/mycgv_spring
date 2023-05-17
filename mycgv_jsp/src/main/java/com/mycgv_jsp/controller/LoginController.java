package com.mycgv_jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class LoginController {
	/*
	 * login.do - �α��� ��
	 */
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public String login() {
		return "/login/login";
	}
	
	
	/*
	 * login_fail.do - �α��� ����ȭ��
	 */
	@RequestMapping(value = "/login_fail.do", method = RequestMethod.GET)
	public String login_fail() {
		return "/login/login_fail";
	}
	
	/*
	 * login_proc.do - �α��� ó��
	 */
	@RequestMapping(value = "/login_proc.do", method = RequestMethod.POST)
	public ModelAndView login_proc(MemberVo memberVo) {
		ModelAndView model = new ModelAndView();
		String viewName = "";
		MemberDao memberDao = new MemberDao();
		int result = memberDao.loginCheck(memberVo);
		if(result == 1) {
			//index.jsp�� �̵�
			//viewResolver�� ȣ�� -> index.do
			model.addObject("login_result", "ok");
			model.setViewName("index"); // sendRedirect
		} else {
			//login_fail.jsp
			model.setViewName("redirect:/login_fail.do"); 
		}
		return model;
	}

}
