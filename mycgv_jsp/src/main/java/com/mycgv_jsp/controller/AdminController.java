package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.MemberDao;
import com.mycgv_jsp.dao.NoticeDao;
import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.NoticeVo;

@Controller
public class AdminController {
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
		NoticeDao noticeDao = new NoticeDao();
		
		//페이징 처리 - startCount, endCount 구하기
		int startCount = 0;
		int endCount = 0;
		int pageSize = 5;	//한페이지당 게시물 수
		int reqPage = 1;	//요청페이지	
		int pageCount = 1;	//전체 페이지 수
		int dbCount = noticeDao.totalRowCount();	//DB에서 가져온 전체 행수
		
		//총 페이지 수 계산
		if(dbCount % pageSize == 0){
			pageCount = dbCount/pageSize;
		}else{
			pageCount = dbCount/pageSize+1;
		}

		//요청 페이지 계산
		if(page != null){
			reqPage = Integer.parseInt(page);
			startCount = (reqPage-1) * pageSize+1; 
			endCount = reqPage *pageSize;
		}else{
			startCount = 1;
			endCount = pageSize;
		}
		
		ArrayList<NoticeVo> list = noticeDao.select(startCount, endCount);
	
		model.addObject("list", list);
		model.addObject("totals", dbCount);
		model.addObject("pageSize", pageSize);
		model.addObject("maxSize", pageCount);
		model.addObject("page", reqPage);
		
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
	public String admin_notice_write_proc(NoticeVo noticeVo) {
		String viewName = "";
		NoticeDao noticeDao = new NoticeDao();
		int result = noticeDao.insert(noticeVo);
		if (result == 1) {
			// viewName = "/admin/notice/admin_notice_list";
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
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);


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
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);
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
		 NoticeDao noticeDao = new NoticeDao(); 
		 int result = noticeDao.update(noticeVo);
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
		 NoticeDao noticeDao = new NoticeDao();
		 int result = noticeDao.delete(nid);
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
		/*
		 * @RequestMapping(value = "/admin_member_list.do" , method =RequestMethod.GET)
		 * public ModelAndView admin_member_list() { ModelAndView model = new
		 * ModelAndView(); MemberDao memberDao = new MemberDao(); ArrayList<MemberVo>
		 * list = memberDao.select(); model.addObject("list", list);
		 * model.setViewName("/admin/member/admin_member_list"); return model; }
		 */
	 
	 @RequestMapping(value = "/admin_member_list.do" , method =RequestMethod.GET)
	 public ModelAndView admin_member_list(String page) { 
		 ModelAndView model = new ModelAndView();
		 MemberDao memberDao = new MemberDao(); 
		//페이징 처리 - startCount, endCount 구하기
			int startCount = 0;
			int endCount = 0;
			int pageSize = 5;	//한페이지당 게시물 수
			int reqPage = 1;	//요청페이지	
			int pageCount = 1;	//전체 페이지 수
			int dbCount = memberDao.totalRowCount();	//DB에서 가져온 전체 행수
			
			//총 페이지 수 계산
			if(dbCount % pageSize == 0){
				pageCount = dbCount/pageSize;
			}else{
				pageCount = dbCount/pageSize+1;
			}

			//요청 페이지 계산
			if(page != null){
				reqPage = Integer.parseInt(page);
				startCount = (reqPage-1) * pageSize+1; 
				endCount = reqPage *pageSize;
			}else{
				startCount = 1;
				endCount = pageSize;
			}
			
	 ArrayList<MemberVo> list = memberDao.select(startCount, endCount);
	 model.addObject("list", list);
	 model.addObject("totals", dbCount);
	 model.addObject("pageSize", pageSize);
	 model.addObject("maxSize", pageCount);
	 model.addObject("page", reqPage);
	 model.setViewName("/admin/member/admin_member_list");
	 return model; 
	 }
}
