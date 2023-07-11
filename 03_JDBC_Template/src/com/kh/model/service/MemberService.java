package com.kh.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.common.Template;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;


/*
 * Service: 기본의 DAO에서 하던 역할을 분담하는 곳
 * 			컨트롤러에서 서비스 호출(Connection객체 생성) 후 서비스를 거쳐 DAO로 넘어간다.
 * 			DAO호출할때 컨넥션 객체와 기존에 넘기고자했던 데이터를 같이 매개변수로 넘겨준다.
 * 			DAO 처리가 끝나면 서비스단에서 결과에 따른 트랜잭션 처리도 해준다.
 * 			-서비스단을 추가함으로써 DAO에는 SQL문 처리 구문만 남게 된다. (역할분담)
 * */
public class MemberService {

	public int insertMember(Member m) {
		//먼저 Connection 객체를 생성한다. (Template에 만들어 놓은 메소드 활용)
		Connection conn = Template.getConnection();
		
		//Dao에게 컨넥션과 데이터 전달하기
		int result = new MemberDao().insertMember(conn,m);
		
		//결과에 따라 트랜잭션 처리하기
		if(result>0) { //성공
			Template.commit(conn);
		}else {//실패
			Template.rollback(conn);
		}
		
		//Connection 반납
		Template.close(conn);
		
		//겨로가 행수 보내기
		return result;
	}

	public ArrayList<Member> SelectAll() {
		Connection conn = Template.getConnection();
		ArrayList<Member> list = new ArrayList<Member>();
		 
		try {
			list = new MemberDao().SelectAll(conn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Template.close(conn);
		
		return list;
	}

	public Member selectById(String id) {
		Connection conn = Template.getConnection();
		Member m = null;
		
		m = new MemberDao().selectById(conn,id);
		
		Template.close(conn);
		
		return m;
	}

	public ArrayList<Member> selectByName(String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		Connection conn = Template.getConnection();
		
		list = new MemberDao().selectByName(conn, name);
		
		Template.close(conn);
		
		return list;
	}

	public int updateMember(String id, String newPwd, String newEmail, String newPhone, String newAddress,
			String newHobby) {
		int result = 0;
		
		Connection conn = Template.getConnection();
		
		result = new MemberDao().updateMember(conn, id, newPwd, newEmail, newPhone, newAddress, newHobby);
		
		if(result>0) {
			Template.commit(conn);
		}else {
			Template.rollback(conn);
		}
		Template.close(conn);
		
		return result;
	}

	public int deleteMember(String deleId) {
		Connection conn = Template.getConnection();
		
		int result = 0;
		
		result = new MemberDao().deleteMember(conn, deleId);
		
		if(result>0) {
			Template.commit(conn);
		}else {
			Template.rollback(conn);
		}
		
		Template.close(conn);
		
		return result;
	}

	

}









