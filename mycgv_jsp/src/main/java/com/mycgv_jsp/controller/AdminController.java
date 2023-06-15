package com.mycgv_jsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.service.FileServiceImpl;
import com.mycgv_jsp.service.MemberService;
import com.mycgv_jsp.service.NoticeService;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class AdminController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private PageServiceImpl pageService;
	@Autowired
	private FileServiceImpl fileService;
	
	
	/*
	 * admin_notice.do - 공지사항
	 */
	@RequestMapping(value = "/admin_index.do", method = RequestMethod.GET)
	public String admin_index() {
		return "/admin/admin_index";
	}

	/*
	 * admin_notice_list.do - 공지사항 리스트
	 */
	@RequestMapping(value = "/admin_notice_list.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_list(String page) {
		ModelAndView model = new ModelAndView();
		Map<String, Integer> param = pageService.getPageResult(page, "notice");
		
		
		ArrayList<NoticeVo> list = noticeService.getList(param.get("startCount"), param.get("endCount"));
		
		model.addObject("list", list);
		model.addObject("totals", param.get("dbCount"));
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		
		model.setViewName("/admin/notice/admin_notice_list");
		
		return model;
	}

	/*
	 * admin_notice_write.do - 공지사항 쓰기폼
	 */
	@RequestMapping(value = "/admin_notice_write.do", method = RequestMethod.GET)
	public String admin_notice_write() {
		return "/admin/notice/admin_notice_write";
	}

	/*
	 * admin_notice_write_proc.do - 공지사항 글쓰기 처리
	 */

	@RequestMapping(value = "/admin_notice_write_proc.do", method = RequestMethod.POST)
	public String admin_notice_write_proc(NoticeVo noticeVo, HttpServletRequest request) throws IOException{
		String viewName = "";
		
		//멀티파일 체크
		int result = noticeService.getInsert(fileService.multiFileCheck(noticeVo));
		if (result == 1) {
			// viewName = "/admin/notice/admin_notice_list";
			if(noticeVo.getFiles()[0].getOriginalFilename() != null) {
				fileService.multiFileSave(noticeVo, request);
			}
			viewName = "redirect:/admin_notice_list.do"; // 수정 후 수정된 리스트를 보여주는 페이지로 갈때 redirect 사용
		} else {
			// 에러 페이지 호출
		}
		return viewName;
	}

	/*
	 * admin_notice_content.do - 공지사항 상세폼
	 */
	@RequestMapping(value = "/admin_notice_content.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		NoticeVo noticeVo = noticeService.getSelect(nid);


		model.addObject("nvo", noticeVo);
		model.setViewName("/admin/notice/admin_notice_content");
		return model;
	}

	/*
	 * admin_notice_update.do - 공지사항 수정폼
	 */
	@RequestMapping(value = "/admin_notice_update.do", method = RequestMethod.GET)
	public ModelAndView admin_notice_update(String nid) {
		// 수정폼은 상세보기 내용을 가져와서 폼에 추가하여 출력
		ModelAndView model = new ModelAndView();
		NoticeVo noticeVo = noticeService.getSelect(nid);
		model.addObject("nvo", noticeVo);
		model.setViewName("/admin/notice/admin_notice_update");
		return model;
	}

	/*
	 * admin_notice_update_proc.do - 공지사항 수정하기 처리
	 */
	 @RequestMapping(value = "/admin_notice_update_proc.do" , method =RequestMethod.POST)
	 public String admin_notice_update_proc(NoticeVo noticeVo) { 
		 String viewName = "";
		 int result = noticeService.getUpdate(noticeVo);
		 if(result == 1) {
			 viewName = "redirect:/admin_notice_list.do";
		 } else {
			 //오류페이지 호출
		 }
	 
	 return viewName; 
	 }
	 
	 /*
	  * admin_notice_delete.do - 공지사항 삭제하기 폼
	  */
	 @RequestMapping(value = "/admin_notice_delete.do", method = RequestMethod.GET)
	 public ModelAndView admin_notice_delete(String nid) {
		 ModelAndView model = new ModelAndView();
		 model.addObject("nid", nid);
		 model.setViewName("admin/notice/admin_notice_delete");
		 return model;
	 }
	 
	 /*
	  * admin_notice_delete_proc.do - 공지사항 삭제처리
	  */
	 @RequestMapping(value = "/admin_notice_delete_proc.do", method = RequestMethod.POST)
	 public String admin_notice_delete_proc(String nid) {
		 String viewName = "";
		 int result = noticeService.getDelete(nid);
		 if(result == 1) {
			 viewName = "redirect:/admin_notice_list.do";
		 } else {
			 //오류페이지 호출
		 }
		 return viewName;
	 }
	 
	 /*
	  * admin_member_list.do - 회원 전체 리스트
	  */
	 
	 @RequestMapping(value = "/admin_member_list.do" , method =RequestMethod.GET)
	 public ModelAndView admin_member_list(String page) { 
		 ModelAndView model = new ModelAndView();
		 Map<String, Integer> param = pageService.getPageResult(page, "member");
			
		ArrayList<MemberVo> list = memberService.getList(param.get("startCount"), param.get("endCount"));
		model.addObject("list", list);
		model.addObject("totals", param.get("dbCount"));
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		model.setViewName("/admin/member/admin_member_list");
		return model; 
	 	}
}
