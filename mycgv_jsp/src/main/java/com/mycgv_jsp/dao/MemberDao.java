package com.mycgv_jsp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycgv_jsp.vo.MemberVo;
import com.mycgv_jsp.vo.SessionVo;


@Repository
public class MemberDao implements MycgvDao{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<Object> select(int startCount, int endCount) {
		Map<String, Integer> param = new HashMap<String, Integer>(); //타입은 map
		param.put("start", startCount);
		param.put("end", endCount);
		
		//List<MemberVo> list = sqlSession.selectList("mapper.member.list", param);
		return sqlSession.selectList("mapper.member.list", param);
//		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
//		String sql = "select rno,id,name, mdate, grade\r\n" + 
//				" from (select rownum rno, id, name, to_char(mdate, 'yyyy-mm-dd') mdate, grade \r\n" + 
//				" from (select id, name, mdate, grade from mycgv_member order by mdate desc))\r\n" + 
//				" where rno between ? and ?";
//		getPreparedStatement(sql);
//		
//		try {
//			pstmt.setInt(1, startCount);
//			pstmt.setInt(2, endCount);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				MemberVo memberVo = new MemberVo();
//				memberVo.setRno(rs.getInt(1));
//				memberVo.setId(rs.getString(2));
//				memberVo.setName(rs.getString(3));
//				memberVo.setMdate(rs.getString(4));
//				memberVo.setGrade(rs.getString(5));
//				list.add(memberVo);
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
	}
	
	
	/* loginCheck - 로그인체크 */
	public SessionVo loginCheck(MemberVo memberVo) {
		return sqlSession.selectOne("mapper.member.login",memberVo);
		/*
		 * int result = 0; String sql =
		 * "select count(*) from mycgv_member where id = ? and pass = ?";
		 * getPreparedStatement(sql);
		 * 
		 * try { pstmt.setString(1, memberVo.getId()); pstmt.setString(2,
		 * memberVo.getPass()); rs = pstmt.executeQuery();
		 * 
		 * while(rs.next()) { result = rs.getInt(1); }
		 * 
		 * }catch (Exception e) { e.printStackTrace(); }
		
		return result;
		*/
	}
	
	
	
	
	/*
	 idCheck - 아이디 중복체크
	 */
	public int idCheck(String id) {
		return sqlSession.selectOne("mapper.member.idCheck", id);
//		int result = 0;
//		String sql = "select count(*) from mycgv_member where id = ?";
//		getPreparedStatement(sql);
//		
//		try {
//			pstmt.setString(1, id);
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				result = rs.getInt(1);
//			}
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
	}
	
	
	
	// insert - 회원가입
	public int insert(Object memberVo) {
		return sqlSession.insert("mapper.member.join", memberVo);
	}
	
//	public int insert(MemberVo memberVo) {
//		return sqlSession.insert("mapper.member.join", memberVo);
//	}
	
	
	//select - 관리자
//	public ArrayList<MemberVo> select() {
//		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
//		String sql = "select rownum rno, id, name, to_char(mdate,'yyyy-mm-dd') mdate, grade \r\n" + 
//				"from (select id, name, mdate, grade from mycgv_member order by mdate desc)";
//		getPreparedStatement(sql);
//		
//		try {
//			rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				MemberVo memberVo = new MemberVo();
//				memberVo.setRno(rs.getInt(1));
//				memberVo.setId(rs.getString(2));
//				memberVo.setName(rs.getString(3));
//				memberVo.setMdate(rs.getString(4));
//				memberVo.setGrade(rs.getString(5));
//				list.add(memberVo);
//			}
//			
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return list;
//	}
	
}
