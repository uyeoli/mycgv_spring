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
	 * notice_list.do - 공지사항 리스트
	 */
	/*
	 * @RequestMapping(value = "/notice_list.do", method = RequestMethod.GET) public
	 * ModelAndView notice_list() { ModelAndView model = new ModelAndView();
	 * NoticeDao noticeDao = new NoticeDao(); ArrayList<NoticeVo> list =
	 * noticeDao.select(); model.addObject("list", list);
	 * model.setViewName("/notice/notice_list");
	 * 
	 * return model; }
	 */
	 
	
	@RequestMapping(value="/notice_list.do", method=RequestMethod.GET)
	public ModelAndView notice_list(String page) {
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
		
		model.setViewName("/notice/notice_list");
		
		return model;
	} 
	
	
	
	@RequestMapping(value = "/notice_content.do", method = RequestMethod.GET)
	public ModelAndView notice_content(String nid) {
		ModelAndView model = new ModelAndView();
		NoticeDao noticeDao = new NoticeDao();
		NoticeVo noticeVo = noticeDao.select(nid);
		if (noticeVo != null) {
			// 조회수 업데이트 - DB적용
			noticeDao.updateHits(nid);
		}
		model.addObject("nvo", noticeVo);
		model.setViewName("/notice/notice_content");
		
		return model;
	}
}
