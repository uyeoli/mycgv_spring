package com.mycgv_jsp.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mycgv_jsp.vo.BoardVo;
import com.mycgv_jsp.vo.NoticeVo;

@Service("fileService")
public class FileServiceImpl {
	/*
	 * multiFileSave - ��Ƽ���� ���� ���
	 */
	public void multiFileSave(NoticeVo noticeVo, HttpServletRequest request) throws IOException{
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		int count = 0;
		 //������ �����ϸ� ������ ����
		for(CommonsMultipartFile file : noticeVo.getFiles()) {
			if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) { //������ ������ �����ϸ�
				 File saveFile = new File(root_path + attach_path + noticeVo.getNsfiles().get(count));
				 file.transferTo(saveFile);
			 }
			count++;
		}
		 
	}
	
	/*
	 * multiFileCheck - ��Ƽ���� üũ ���
	 */
	public NoticeVo multiFileCheck(NoticeVo noticeVo) {
		
		for(CommonsMultipartFile file : noticeVo.getFiles()) {
			if(!file.getOriginalFilename().equals("")) { 
				//���� ����
				UUID uuid = UUID.randomUUID();
				noticeVo.getNfiles().add(file.getOriginalFilename());
				noticeVo.getNsfiles().add(uuid + "_" + file.getOriginalFilename());
			} else { 
				//���� ����
				noticeVo.getNfiles().add("");
				noticeVo.getNsfiles().add("");
			}
		}
		noticeVo.setNfile1(noticeVo.getNfiles().get(0));
		noticeVo.setNsfile1(noticeVo.getNsfiles().get(0));
		noticeVo.setNfile2(noticeVo.getNfiles().get(1));
		noticeVo.setNsfile2(noticeVo.getNsfiles().get(1));
		
		return noticeVo;
	}
	
	/*
	 * fileDelete - ���� ���� ����
	 */
	public void fileDelete(HttpServletRequest request, String oldFileName) throws IOException {
		
		 //������ ���� ��ġ
		 String root_path = request.getSession().getServletContext().getRealPath("/"); // ������ ������ġ
		 String attach_path = "\\resources\\upload\\";
		 
		 //������ �̹� �����ϸ� ����
		 if(!oldFileName.equals("") && oldFileName != null) {
			 File deleteFile = new File(root_path + attach_path + oldFileName);
			 if(deleteFile.exists()) {
				 deleteFile.delete();
			 }
		 }
	}
	
	/*
	 * fileDelete - ���� ���� ����
	 */
	public void fileDelete(BoardVo boardVo, HttpServletRequest request, String oldFileName) throws IOException {
		
		 //������ ���� ��ġ
		 String root_path = request.getSession().getServletContext().getRealPath("/"); // ������ ������ġ
		 String attach_path = "\\resources\\upload\\";
		 
		 //������ �̹� �����ϸ� ����
		 if(!boardVo.getFile1().getOriginalFilename().equals("")) { //���ο� ���� ����
			 File deleteFile = new File(root_path + attach_path + oldFileName);
			 if(deleteFile.exists()) {
				 deleteFile.delete();
			 }
		 }
	}
	
	/*
	 * fileSave ��� - ������ �����ϸ� ������ �����ϴ� �޼ҵ�
	 */
	public void fileSave(BoardVo boardVo, HttpServletRequest request) throws IOException{
		 //������ ���� ��ġ
		 String root_path = request.getSession().getServletContext().getRealPath("/"); // ������ ������ġ
		 String attach_path = "\\resources\\upload\\";
		 //������ �����ϸ� ������ ����
		 if(boardVo.getBfile() != null && !boardVo.getBsfile().equals("")) {
			 System.out.println("save file -->" + boardVo.getBfile());
			 File saveFile = new File(root_path + attach_path + boardVo.getBsfile());
			 boardVo.getFile1().transferTo(saveFile);
		 }
	}
	
	/*
	 * fileCheck ��� - ������ �����ϸ� boardVo�� bfile, bsfile set!, ������ boardVo ����! 
	 */
	public BoardVo fileCheck(BoardVo boardVo) {
		if(boardVo.getFile1().getOriginalFilename() != null 
				&& !boardVo.getFile1().getOriginalFilename().equals("")) { // ������ ������ �����ϸ�
		 
			 //BSFILE ���� �ߺ� ó��
			 UUID uuid = UUID.randomUUID();
			 String bfile = boardVo.getFile1().getOriginalFilename();
			 String bsfile = uuid + "_" + bfile;
			 System.out.println(bfile);
			 System.out.println(bsfile);
			 
			 boardVo.setBfile(bfile);
			 boardVo.setBsfile(bsfile);
		 } else {
			 System.out.println("���� ����");
//			 boardVo.setBfile("");
//			 boardVo.setBsfile("");
		 }
			return boardVo;
	}
	
	
}
