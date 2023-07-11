package com.kh.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.common.Template;
import com.kh.model.vo.Member;

public class MemberDao {

	//Service에서 생성 후 전달한 Connection 객체 매개변수로 받아서 사용하기
	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = 0; //처리된 행수 돌려받을 변수
		
		String sql ="INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			//처리된 행 수 받기
			result = pstmt.executeUpdate();
			
			//트랜잭션 처리는 Service에서 진행
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//Template에 있는 close메소드로 자원 반납
			Template.close(pstmt);
		}
		return result;
	}

	public ArrayList<Member> SelectAll(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		
		String sql = "SELECT * FROM MEMBER";
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				Member m = new Member(rset.getInt("USERNO"),
									  rset.getString("USERID"),
									  rset.getString("USERPWD"),
									  rset.getString("USERNAME"),
									  rset.getString("GENDER"),
									  rset.getInt("AGE"),
									  rset.getString("EMAIL"),
									  rset.getString("PHONE"),
									  rset.getString("ADDRESS"),
									  rset.getString("HOBBY"),
									  rset.getDate("ENROLLDATE"));
				
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Template.close(rset);
			Template.close(stmt);
		}
		
		return list;
	}

	public Member selectById(Connection conn, String id) {

		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("GENDER"),
							   rset.getInt("AGE"),
							   rset.getString("EMAIL"),
							   rset.getString("PHONE"),
							   rset.getString("ADDRESS"),
							   rset.getString("HOBBY"),
							   rset.getDate("ENROLLDATE"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return m;
	}

	public ArrayList<Member> selectByName(Connection conn, String name) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		Member m = null;
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+name+"%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("GENDER"),
							   rset.getInt("AGE"),
							   rset.getString("EMAIL"),
							   rset.getString("PHONE"),	
							   rset.getString("ADDRESS"),
							   rset.getString("HOBBY"),
							   rset.getDate("ENROLLDATE"));
				
				list.add(m);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
	}

	public int updateMember(Connection conn, String id, String newPwd, String newEmail, String newPhone, String newAddress,
			String newHobby) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?, HOBBY = ? WHERE USERID = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPwd);
			pstmt.setString(2, newEmail);
			pstmt.setString(3, newPhone);
			pstmt.setString(4, newAddress);
			pstmt.setString(5, newHobby);
			pstmt.setString(6, id);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String deleId) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
				
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, deleId);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	

	
}


