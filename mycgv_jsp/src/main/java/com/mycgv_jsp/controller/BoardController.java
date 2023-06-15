package com.mycgv_jsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycgv_jsp.service.BoardService;
import com.mycgv_jsp.service.FileServiceImpl;
import com.mycgv_jsp.service.PageServiceImpl;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private PageServiceImpl pageService;
	@Autowired
	private FileServiceImpl fileService;
	
	 //heder 게시판(json) 호출되는 주소
	@RequestMapping(value="/board_list_json.do", method=RequestMethod.GET)
	public String board_list_json() {
		return "/board/board_list_json";
	}
	
 	/**
	 * board_list_json_data.do - ajax에서 호출되는 게시글 전체 리스트(JSON)
	 * @return
	 */
	@RequestMapping(value="/board_list_json_data.do", method=RequestMethod.GET,
						produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String board_list_json_data(String page) {
		//BoardDao boardDao = new BoardDao();
		//페이징 처리 - startCount, endCount 구하기
		Map<String,Integer> param = pageService.getPageResult(page, "board");
		
		ArrayList<BoardVo> list = boardService.getList(param.get("startCount"), param.get("endCount"));
		//list 객체의 데이터를 JSON 형태로 생성
		JsonObject jlist = new JsonObject();
		JsonArray jarray = new JsonArray();
		
		for(BoardVo boardVo : list) {
			JsonObject jobj = new JsonObject(); // {}
			jobj.addProperty("rno", boardVo.getRno()); //{rno:1}
			jobj.addProperty("btitle", boardVo.getBtitle()); // {rno:1, btitle:"df"}
			jobj.addProperty("id", boardVo.getId());
			jobj.addProperty("bhits", boardVo.getBhits());
			jobj.addProperty("bdate", boardVo.getBdate());
			jarray.add(jobj);
		}
		jlist.add("jlist", jarray);
		jlist.addProperty("totals", param.get("dbCount"));
		jlist.addProperty("pageSize", param.get("pageSize"));
		jlist.addProperty("maxSize", param.get("maxSize"));
		jlist.addProperty("page", param.get("page"));
		
		return new Gson().toJson(jlist);
	}

	
	 
	 /*
	  * board_content.do - 게시글 상세 보기
	  */
	 @RequestMapping(value = "/board_content.do" , method = RequestMethod.GET) 
	 public ModelAndView board_content(String bid) {
		 ModelAndView model = new ModelAndView();
		 //BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardService.getSelect(bid);
		 if(boardVo != null) {
			 //조회수 업데이트 - DB적용
			 boardService.getUpdateHits(bid);
		 }
		 
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_content");
		 return model;
	 }
	 
 	/**
	 * board_list.do - 게시글 전체 리스트 
	 * @return
	 */
	@RequestMapping(value="/board_list.do", method=RequestMethod.GET)
	public ModelAndView board_list(String page) {
		ModelAndView model = new ModelAndView();		
		//BoardDao boardDao = new BoardDao();
		
		Map<String,Integer> param = pageService.getPageResult(page, "board");
		
		ArrayList<BoardVo> list = boardService.getList(param.get("startCount"), param.get("endCount"));
	
		model.addObject("list", list);
		model.addObject("totals", param.get("dbCount"));
		model.addObject("pageSize", param.get("pageSize"));
		model.addObject("maxSize", param.get("maxSize"));
		model.addObject("page", param.get("page"));
		
		model.setViewName("/board/board_list");
		
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
	 public String board_write_proc(BoardVo boardVo, HttpServletRequest request) throws Exception {
		 //1. 폼에서 넘어오는 데이터 BoardVo에 담기
		 //2. BoardVo 데이터를 Dao에 전송
		 //3. mycgv_board 테이블에 insert
		 String viewName = "";
		 
		 //BoardVo boardVo2 = fileService.fileCheck(boardVo);
		 //BoardDao boardDao = new BoardDao();
		 int result = boardService.getInsert(fileService.fileCheck(boardVo));
		 if(result == 1) {
			 
			 if(boardVo.getBfile() != null && boardVo.getBfile().equals("")) {
				 fileService.fileSave(boardVo, request);
			 }
			 
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
		 //BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardService.getSelect(bid);
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_update");
		 return model;
	 }
	 
	 /*
	  * board_update_proc.do - 게시글 수정하기 처리
	  */
	 @RequestMapping(value = "/board_update_proc.do", method = RequestMethod.POST)
	 public String board_update_proc(BoardVo boardVo, HttpServletRequest request) throws IOException{
		 String viewName = "";
		 
		 String oldFileName = boardVo.getBsfile(); //새로운 파일 업데이트 시 기존파일 삭제
		 int result = boardService.getUpdate(fileService.fileCheck(boardVo));
		 if(result == 1) {
			 if(boardVo.getBfile() != null && boardVo.getBfile().equals("")) {
				 fileService.fileSave(boardVo, request); // 새로운 파일 저장
				 // 기존파일 삭제
				 fileService.fileDelete(boardVo, request, oldFileName);
			 }
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
	 public ModelAndView board_delete(String bid, String bsfile) {
		 //수정폼은 상세보기 내용을 가져와서 폼에 추가하여 출력
		 ModelAndView model = new ModelAndView();
		 model.addObject("bid", bid);
		 model.addObject("bsfile", bsfile);
		 model.setViewName("/board/board_delete");
		 return model;
	 }
	 
	 /*
	  * board_delete_proc.do - 게시글 삭제하기 처리
	  */
	 @RequestMapping(value = "/board_delete_proc.do", method = RequestMethod.POST)
	 public String board_delete_proc(String bid, String bsfile, HttpServletRequest request) throws IOException{
		 String viewName = "";
		 //BoardDao boardDao = new BoardDao();
		 int result = boardService.getDelete(bid);
		 if(result == 1) {
			 if(bsfile != null) {
				 // 기존파일 삭제
				 fileService.fileDelete(request, bsfile);
			 }
			viewName = "redirect:/board_list.do";
		 } else {
			 //오류 페이지 호출
		 }
		 return viewName;
	 }
}
