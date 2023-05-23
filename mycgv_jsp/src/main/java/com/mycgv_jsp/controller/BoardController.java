package com.mycgv_jsp.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mycgv_jsp.dao.BoardDao;
import com.mycgv_jsp.vo.BoardVo;

@Controller
public class BoardController {
	
	/*
	 * board_list.do - �Խñ� ��ü ����Ʈ
	 */
	/*
	 * @RequestMapping(value = "/board_list.do" , method = RequestMethod.GET) 
	 * public ModelAndView board_list() {
	 * 
	 * ModelAndView model = new ModelAndView(); 
	 * //DB���� ��
	 * BoardDao boardDao = new BoardDao(); 
	 *  //�Խñ� ��ü����Ʈ �������� 
	 * ArrayList<BoardVo> list = boardDao.select();
	 * 
	 * model.addObject("list", list); 
	 * model.setViewName("/board/board_list"); 
	 * return model; 
	 * }
	 * 
	 */
	 //heder �Խ���(json) ȣ��Ǵ� �ּ�
	@RequestMapping(value="/board_list_json.do", method=RequestMethod.GET)
	public String board_list_json() {
		return "/board/board_list_json";
	}
	
	
	 	/**
		 * board_list_json_data.do - ajax���� ȣ��Ǵ� �Խñ� ��ü ����Ʈ(JSON)
		 * @return
		 */
		@RequestMapping(value="/board_list_json_data.do", method=RequestMethod.GET,
							produces = "text/plain;charset=UTF-8")
		@ResponseBody
		public String board_list_json_data(String page) {
			BoardDao boardDao = new BoardDao();
			//����¡ ó�� - startCount, endCount ���ϱ�
			int startCount = 0;
			int endCount = 0;
			int pageSize = 5;	//���������� �Խù� ��
			int reqPage = 1;	//��û������	
			int pageCount = 1;	//��ü ������ ��
			int dbCount = boardDao.totalRowCount();	//DB���� ������ ��ü ���
			
			//�� ������ �� ���
			if(dbCount % pageSize == 0){
				pageCount = dbCount/pageSize;
			}else{
				pageCount = dbCount/pageSize+1;
			}

			//��û ������ ���
			if(page != null){
				reqPage = Integer.parseInt(page);
				startCount = (reqPage-1) * pageSize+1; 
				endCount = reqPage *pageSize;
			}else{
				startCount = 1;
				endCount = pageSize;
			}
			
			ArrayList<BoardVo> list = boardDao.select(startCount, endCount);
			//list ��ü�� �����͸� JSON ���·� ����
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
			jlist.addProperty("totals", dbCount);
			jlist.addProperty("pageSize", pageSize);
			jlist.addProperty("maxSize", pageCount);
			jlist.addProperty("page", reqPage);
			
			return new Gson().toJson(jlist);
		}
			
			
//			model.addObject("list", list);
//			model.addObject("totals", dbCount);
//			model.addObject("pageSize", pageSize);
//			model.addObject("maxSize", pageCount);
//			model.addObject("page", reqPage);
//			
//			model.setViewName("/board/board_list");
			
			
	
	 
	 /*
	  * board_content.do - �Խñ� �� ����
	  */
	 @RequestMapping(value = "/board_content.do" , method = RequestMethod.GET) 
	 public ModelAndView board_content(String bid) {
		 ModelAndView model = new ModelAndView();
		 BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardDao.select(bid);
		 if(boardVo != null) {
			 //��ȸ�� ������Ʈ - DB����
			 boardDao.updateHits(bid);
		 }
		 
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_content");
		 return model;
	 }
	 
	 	/**
		 * board_list.do - �Խñ� ��ü ����Ʈ 
		 * @return
		 */
		@RequestMapping(value="/board_list.do", method=RequestMethod.GET)
		public ModelAndView board_list(String page) {
			ModelAndView model = new ModelAndView();		
			BoardDao boardDao = new BoardDao();
			
			//����¡ ó�� - startCount, endCount ���ϱ�
			int startCount = 0;
			int endCount = 0;
			int pageSize = 5;	//���������� �Խù� ��
			int reqPage = 1;	//��û������	
			int pageCount = 1;	//��ü ������ ��
			int dbCount = boardDao.totalRowCount();	//DB���� ������ ��ü ���
			
			//�� ������ �� ���
			if(dbCount % pageSize == 0){
				pageCount = dbCount/pageSize;
			}else{
				pageCount = dbCount/pageSize+1;
			}

			//��û ������ ���
			if(page != null){
				reqPage = Integer.parseInt(page);
				startCount = (reqPage-1) * pageSize+1; 
				endCount = reqPage *pageSize;
			}else{
				startCount = 1;
				endCount = pageSize;
			}
			
			ArrayList<BoardVo> list = boardDao.select(startCount, endCount);
		
			model.addObject("list", list);
			model.addObject("totals", dbCount);
			model.addObject("pageSize", pageSize);
			model.addObject("maxSize", pageCount);
			model.addObject("page", reqPage);
			
			model.setViewName("/board/board_list");
			
			return model;
		} 
	 
	 
	 /*
	  * board_write.do - �Խñ� �۾���
	  */
	 @RequestMapping(value = "/board_write.do" , method = RequestMethod.GET) 
	 public String board_write() {
		 
		 return "/board/board_write";
	 }
		 
	 /*
	  * board_write_proc.do - �Խñ� �۾��� ó��
	  */
	 
	 @RequestMapping(value = "/board_write_proc.do" , method = RequestMethod.POST) 
	 public String board_write_proc(BoardVo boardVo) {
		 //1. ������ �Ѿ���� ������ BoardVo�� ���
		 //2. BoardVo �����͸� Dao�� ����
		 //3. mycgv_board ���̺� insert
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.insert(boardVo);
		 if(result == 1) {
			 //viewName = "/board/board_list";
			 viewName = "redirect:/board_list.do"; //���� �� ������ ����Ʈ�� �����ִ� �������� ���� redirect ���
		 } else {
			 //���� ������ ȣ��
		 }
		 return viewName;
	 }
	 
	 /*
	  * board_update.do - �Խñ� ���� ��
	  */
	 @RequestMapping(value = "/board_update.do", method = RequestMethod.GET)
	 public ModelAndView board_update(String bid) {
		 //�������� �󼼺��� ������ �����ͼ� ���� �߰��Ͽ� ���
		 ModelAndView model = new ModelAndView();
		 BoardDao boardDao = new BoardDao();
		 BoardVo boardVo = boardDao.select(bid);
		 model.addObject("bvo", boardVo);
		 model.setViewName("/board/board_update");
		 return model;
	 }
	 
	 /*
	  * board_update_proc.do - �Խñ� �����ϱ� ó��
	  */
	 @RequestMapping(value = "/board_update_proc.do", method = RequestMethod.POST)
	 public String board_update_proc(BoardVo boardVo) {
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.update(boardVo);
		 if(result == 1) {
			 viewName = "redirect:/board_list.do";
		 } else {
			 //���������� ȣ��
		 }
		 return viewName;
	 }
	 
	 /*
	  * board_delete.do - �Խñ� ���� ��
	  */
	 @RequestMapping(value = "/board_delete.do", method = RequestMethod.GET)
	 public ModelAndView board_delete(String bid) {
		 //�������� �󼼺��� ������ �����ͼ� ���� �߰��Ͽ� ���
		 ModelAndView model = new ModelAndView();
		 model.addObject("bid", bid);
		 model.setViewName("/board/board_delete");
		 return model;
	 }
	 
	 /*
	  * board_delete_proc.do - �Խñ� �����ϱ� ó��
	  */
	 @RequestMapping(value = "/board_delete_proc.do", method = RequestMethod.POST)
	 public String board_delete_proc(String bid) {
		 String viewName = "";
		 BoardDao boardDao = new BoardDao();
		 int result = boardDao.delete(bid);
		 if(result == 1) {
			viewName = "redirect:/board_list.do";
		 } else {
			 //���� ������ ȣ��
		 }
		 return viewName;
	 }
}
