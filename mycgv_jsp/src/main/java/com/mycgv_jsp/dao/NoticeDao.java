package com.mycgv_jsp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycgv_jsp.vo.NoticeVo;

@Repository
public class NoticeDao extends DBConn{
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	/* 전체 리스트 */
	public ArrayList<NoticeVo> select(int startCount, int endCount){
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("start", startCount);
		param.put("end", endCount);
		List<NoticeVo> list = sqlSession.selectList("mapper.notice.list", param);
		return (ArrayList<NoticeVo>)list;
//		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
//		try{
//			//String sql = "SELECT ROWNUM RNO, NO, TITLE, to_char(NDATE,'yyyy-mm-dd'), HITS "
//			//			+ " FROM (SELECT * FROM CGV_NOTICE ORDER BY NDATE DESC)";
//			String sql = "select rno, nid, ntitle, ncontent, nhits, ndate\r\n" + 
//					"from (select rownum rno, nid, ntitle, ncontent, nhits, to_char(ndate, 'yyyy-mm-dd') ndate\r\n" + 
//					"          from (select nid, ntitle, ncontent, nhits, ndate from mycgv_notice order by ndate desc))\r\n" + 
//					"                where rno between ? and ?";
//			
//			getPreparedStatement(sql);
//			pstmt.setInt(1, startCount);
//			pstmt.setInt(2, endCount);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()){
//				NoticeVo vo = new NoticeVo();
//				vo.setRno(rs.getInt(1));
//				vo.setNid(rs.getString(2));
//				vo.setNtitle(rs.getString(3));
//				vo.setNcontent(rs.getString(4));
//				vo.setNhits(rs.getInt(5));
//				vo.setNdate(rs.getString(5));
//				list.add(vo);
//			}
//			
//		}catch(Exception e){ 
//			e.printStackTrace();
//		}
//		return list;
	}
	
	
	
	
	/*
	 * SELECT - 공지사항 전체 리스트
	 */
	public ArrayList<NoticeVo> select() {
		ArrayList<NoticeVo> list = new ArrayList<NoticeVo>();
		String sql = "SELECT ROWNUM RNO, NID, NTITLE, NCONTENT, NHITS, TO_CHAR(NDATE,'YYYY-MM-DD') NDATE\r\n" + 
				" FROM (SELECT * FROM MYCGV_NOTICE ORDER BY NID DESC)";
		getPreparedStatement(sql);
		try {
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoticeVo noticeVo = new NoticeVo();
				noticeVo.setRno(rs.getInt(1));
				noticeVo.setNid(rs.getString(2));
				noticeVo.setNtitle(rs.getString(3));
				noticeVo.setNcontent(rs.getString(4));
				noticeVo.setNhits(rs.getInt(5));
				noticeVo.setNdate(rs.getString(6));
				list.add(noticeVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int totalRowCount() {
		int count = 0;
		String sql = "select count(*) from mycgv_notice";
		getPreparedStatement(sql);
		
		try {
			rs = pstmt.executeQuery();
			while(rs.next()) {				
				count = rs.getInt(1);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;		
	}	
	
	
	/*
	 * 공지사항 글쓰기 처리
	 */
	public int insert(NoticeVo noticeVo) {
		return sqlSession.insert("mapper.notice.insert", noticeVo);
//		int result = 0;
//		String sql = "INSERT INTO MYCGV_NOTICE VALUES('n_'||ltrim(to_char(sequ_mycgv_notice.nextval, '0000')),\r\n" + 
//				"?,?,0,SYSDATE)";
//		getPreparedStatement(sql);
//		try {
//			pstmt.setString(1, noticeVo.getNtitle());
//			pstmt.setString(2, noticeVo.getNcontent());
//			
//			result = pstmt.executeUpdate();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
	}
	
	public NoticeVo select(String nid) {
		return sqlSession.selectOne("mapper.notice.content", nid);
//		NoticeVo noticeVo = new NoticeVo();
//		String sql = "SELECT NID, NTITLE, NCONTENT, NHITS, TO_CHAR(NDATE,'YYYY-MM-DD') NDATE FROM MYCGV_NOTICE WHERE NID = ?";
//		getPreparedStatement(sql);
//		try {
//			pstmt.setString(1, nid);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				noticeVo.setNid(rs.getString(1));
//				noticeVo.setNtitle(rs.getString(2));
//				noticeVo.setNcontent(rs.getString(3));
//				noticeVo.setNhits(rs.getInt(4));
//				noticeVo.setNdate(rs.getString(5));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return noticeVo;
	}
	
	/*
	 * 공지사항 조회수 처리
	 */
	public void updateHits(String nid) {
		sqlSession.update("mapper.notice.updateHits", nid);
//		String sql = "update mycgv_notice set nhits = nhits + 1 where nid = ?";
//		getPreparedStatement(sql);
//		try {
//			pstmt.setString(1, nid);
//			pstmt.executeUpdate();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	/*
	 * 공지사항 수정 처리
	 */
	public int update(NoticeVo noticeVo) {
		return sqlSession.update("mapper.notice.update", noticeVo);
//		int result = 0;
//		String sql = "update mycgv_notice set ntitle = ?, ncontent = ? where nid = ?";
//		getPreparedStatement(sql);
//		try {
//			pstmt.setString(1, noticeVo.getNtitle());
//			pstmt.setString(2, noticeVo.getNcontent());
//			pstmt.setString(3, noticeVo.getNid());
//			
//			result = pstmt.executeUpdate();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
	}
	
	/*
	 * 공지사항 삭제처리
	 */
	public int delete(String nid) {
		return sqlSession.delete("mapper.notice.delete", nid);
//		int result = 0;
//		String sql = "delete from mycgv_notice where nid = ?";
//		getPreparedStatement(sql);
//		try {
//			pstmt.setString(1, nid);
//			result = pstmt.executeUpdate();
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
	}
}
