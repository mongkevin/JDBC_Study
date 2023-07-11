package com.kh.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.common.Template;
import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;

public class MemberService {

	public int insertMember(Member m) {
		
		Connection conn = Template.getConnection();
		//dao에 전달받은 데이터와 커넥션 객체 전달
		int result = new MemberDao().insertMember(conn,m);
		
		//트랜잭션 처리
		if(result>0) {
			Template.commit(conn);
		}else {
			Template.rollback(conn);
		}
		
		Template.close(conn);
		//반환 
		return result;
	}

	public ArrayList<Member> selectAll() {
		Connection conn = Template.getConnection();
		
		ArrayList<Member> list = new MemberDao().selectAll(conn);
		
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
		Connection conn = Template.getConnection();
		ArrayList<Member> list = new ArrayList<Member>();
		list = new MemberDao().selectByName(conn, name);
		Template.close(conn);
		return list;
	}

	public int updateMember(Member m, String id) {
		int result = 0;
		Connection conn = Template.getConnection();
		result = new MemberDao().updateMember(conn,m,id);
		
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
