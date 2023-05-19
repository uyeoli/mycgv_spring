package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.NoticeDao;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class NoticeController {
	
	
	/*
	 * notice_list.do - �������� ����Ʈ
	 */
	@RequestMapping(value = "/notice_list.do", method = RequestMethod.GET)
	public ModelAndView notice_list() {
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		ArrayList<NoticeVo> list = noticeDao.select();
		model.addObject("list", list);
		model.setViewName("/notice/notice_list");

		return model;
	}
	 
	@RequestMapping(value = "/notice_content.do", method = RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);
		if (noticeVo != null) {
			// ��ȸ�� ������Ʈ - DB����
			noticeDao.updateHits(nid);
		}
		model.addObject("nvo", noticeVo);
		model.setViewName("/notice/notice_content");
		
		return model;
	}
}
