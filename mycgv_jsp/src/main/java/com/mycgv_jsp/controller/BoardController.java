package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	/*
	 * board_list.do - 게시글 전체 리스트
	 */
	 @RequestMapping(value = "/board_list.do" , method = RequestMethod.GET) 
	 public ModelAndView board_list() { 
		 
		 ModelAndView model = new ModelAndView();
		 //DB연동 후 ArrayList<BoardVo>
		 BoardDao boardDao = new BoardDao();
		 //게시글 전체리스트 가져오기
		 ArrayList<BoardVo> list = boardDao.select();
		 
		 model.addObject("list", list);
		 model.setViewName("/board/board_list");
		 return model; 
		}
	 
	 
	 /*
	  * board_content.do - 게시글 상세 보기
	  */
	 @RequestMapping(value = "/board_content.do" , method = RequestMethod.GET) 
	 public ModelAndView board_content(String bid) {
		 ModelAndView model = new ModelAndView();
		 BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardDao.select(bid);
		 if(boardVo != null) {
			 //조회수 업데이트 - DB적용
			 boardDao.updateHits(bid);
		 }
		 
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_content");
		 return model;
	 }
	 
	 /*
	  * board_write.do - 게시글 글쓰기
	  */
	 @RequestMapping(value = "/board_write.do" , method = RequestMethod.GET) 
	 public String board_write() {
		 
		 return "/board/board_write";
	 }
		 
	 /*
	  * board_write_proc.do - 게시글 글쓰기 처리
	  */
	 
	 @RequestMapping(value = "/board_write_proc.do" , method = RequestMethod.POST) 
	 public String board_write_proc(BoardVo boardVo) {
		 //1. 폼에서 넘어오는 데이터 BoardVo에 담기
		 //2. BoardVo 데이터를 Dao에 전송
		 //3. mycgv_board 테이블에 insert
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.insert(boardVo);
		 if(result == 1) {
			 //viewName = "/board/board_list";
			 viewName = "redirect:/board_list.do"; //수정 후 수정된 리스트를 보여주는 페이지로 갈때 redirect 사용
		 } else {
			 //에러 페이지 호출
		 }
		 return viewName;
	 }
	 
	 /*
	  * board_update.do - 게시글 수정 폼
	  */
	 @RequestMapping(value = "/board_update.do", method = RequestMethod.GET)
	 public ModelAndView board_update(String bid) {
		 //수정폼은 상세보기 내용을 가져와서 폼에 추가하여 출력
		 ModelAndView model = new ModelAndView();
		 BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardDao.select(bid);
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_update");
		 return model;
	 }
	 
	 /*
	  * board_update_proc.do - 게시글 수정하기 처리
	  */
	 @RequestMapping(value = "/board_update_proc.do", method = RequestMethod.POST)
	 public String board_update_proc(BoardVo boardVo) {
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.update(boardVo);
		 if(result == 1) {
			 viewName = "redirect:/board_list.do";
		 } else {
			 //에러페이지 호출
		 }
		 return viewName;
	 }
	 
	 /*
	  * board_delete.do - 게시글 삭제 폼
	  */
	 @RequestMapping(value = "/board_delete.do", method = RequestMethod.GET)
	 public ModelAndView board_delete(String bid) {
		 //수정폼은 상세보기 내용을 가져와서 폼에 추가하여 출력
		 ModelAndView model = new ModelAndView();
		 model.addObject("bid", bid);
		 model.setViewName("/board/board_delete");
		 return model;
	 }
	 
	 /*
	  * board_delete_proc.do - 게시글 삭제하기 처리
	  */
	 @RequestMapping(value = "/board_delete_proc.do", method = RequestMethod.POST)
	 public String board_delete_proc(String bid) {
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.delete(bid);
		 if(result == 1) {
			viewName = "redirect:/board_list.do";
		 } else {
			 //오류 페이지 호출
		 }
		 return viewName;
	 }
}
