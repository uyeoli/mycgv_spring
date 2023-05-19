package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.dao.NoticeDao;
import com.mycgv_jsp.vo.BoardVo;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class AdminController {
	/*
	 * admin_notice.do - ��������
	 */
	@RequestMapping(value = "/admin_index.do", method = RequestMethod.GET)
	public String admin_index() {
		return "/admin/admin_index";
	}

	/*
	 * admin_notice_list.do - �������� ����Ʈ
	 */
	@RequestMapping(value = "/admin_notice_list.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_list() {
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		ArrayList<NoticeVo> list = noticeDao.select();
		model.addObject("list", list);
		model.setViewName("/admin/notice/admin_notice_list");

		return model;
	}

	/*
	 * admin_notice_write.do - �������� ������
	 */
	@RequestMapping(value = "/admin_notice_write.do", method = RequestMethod.GET)
	public String admin_notice_write() {
		return "/admin/notice/admin_notice_write";
	}

	/*
	 * admin_notice_write_proc.do - �������� �۾��� ó��
	 */

	@RequestMapping(value = "/admin_notice_write_proc.do", method = RequestMethod.POST)
	public String admin_notice_write_proc(NoticeVo noticeVo) {
		String viewName = "";
		NoticeDao noticeDao = new NoticeDao();
		int result = noticeDao.insert(noticeVo);
		if (result == 1) {
			// viewName = "/admin/notice/admin_notice_list";
			viewName = "redirect:/admin_notice_list.do"; // ���� �� ������ ����Ʈ�� �����ִ� �������� ���� redirect ���
		} else {
			// ���� ������ ȣ��
		}
		return viewName;
	}

	/*
	 * admin_notice_content.do - �������� ����
	 */
	@RequestMapping(value = "/admin_notice_content.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);


		model.addObject("nvo", noticeVo);
		model.setViewName("/admin/notice/admin_notice_content");
		return model;
	}

	/*
	 * admin_notice_update.do - �������� ������
	 */
	@RequestMapping(value = "/admin_notice_update.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_update(String nid) {
		// �������� �󼼺��� ������ �����ͼ� ���� �߰��Ͽ� ���
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);
		model.addObject("nvo", noticeVo);
		model.setViewName("/admin/notice/admin_notice_update");
		return model;
	}

	/*
	 * admin_notice_update_proc.do - �������� �����ϱ� ó��
	 */
	 @RequestMapping(value = "/admin_notice_update_proc.do" , method =RequestMethod.POST)
	 public String admin_notice_update_proc(NoticeVo noticeVo) { 
		 String viewName = "";
		 NoticeDao noticeDao = new NoticeDao(); 
		 int result = noticeDao.update(noticeVo);
		 if(result == 1) {
			 viewName = "redirect:/admin_notice_list.do";
		 } else {
			 //���������� ȣ��
		 }
	 
	 return viewName; 
	 }
	 
	 /*
	  * admin_notice_delete.do - �������� �����ϱ� ��
	  */
	 @RequestMapping(value = "/admin_notice_delete.do", method = RequestMethod.GET)
	 public ModelAndView admin_notice_delete(String nid) {
		 ModelAndView model = new ModelAndView();
		 model.addObject("nid", nid);
		 model.setViewName("admin/notice/admin_notice_delete");
		 return model;
	 }
	 
	 /*
	  * admin_notice_delete_proc.do - �������� ����ó��
	  */
	 @RequestMapping(value = "/admin_notice_delete_proc.do", method = RequestMethod.POST)
	 public String admin_notice_delete_proc(String nid) {
		 String viewName = "";
		 NoticeDao noticeDao = new NoticeDao();
		 int result = noticeDao.delete(nid);
		 if(result == 1) {
			 viewName = "redirect:/admin_notice_list.do";
		 } else {
			 //���������� ȣ��
		 }
		 return viewName;
	 }
	 
}
