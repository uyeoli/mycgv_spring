package com.mycgv_jsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.vo.MemberVo;

@Controller
public class JoinController {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/join.do", method = RequestMethod.GET)
	public String login() {
		return "/join/join";
	}
	
	/*
	 * join_proc.do - ȸ������
	 */
	@RequestMapping(value = "/join_proc.do", method = RequestMethod.POST)
	public ModelAndView join_proc(MemberVo memberVo) {
		ModelAndView model = new ModelAndView();
		//String viewName = "";
//		MemberDao memberDao = new MemberDao();
//		int result = memberDao.insert(memberVo);
		int result = memberService.getJoinResult(memberVo);
		if(result == 1) {
			model.addObject("join_result", "ok");
			model.setViewName("login/login");
		} else {
			//ȸ������ ���� - ���������� ȣ��
			//viewName = "redirect:/join_fail.do";
		}
		return model;
	}
	
	/*
	 * id_check.do - ���̵� �ߺ�üũ ó��
	 */
	@RequestMapping(value = "/id_check.do", method = RequestMethod.GET)
	@ResponseBody // ajax���
	public String id_check(String id) {
		return memberService.getIdCheckResult(id);
	}
}
