package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.kh.common.Template;
import com.kh.model.vo.Member;

public class MemberDao {

	//파일 읽어올 객체 생성
	private Properties prop = new Properties();
	
	//service단에서 new MemberDao()를 할때마다 파일을 읽어서 변경사항을 적용시킬 수 있게끔 작업하기
	//기본생성자에 코드 작성
	
	public MemberDao() {
		try {
			//resources폴더에 있는 query.xml파일 읽어서 prop에 세팅
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertMember(Connection conn, Member m) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");
		
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
							
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Member> selectAll(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();
		Member m = null;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(prop.getProperty("selectAll"));
			
			while(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("GENDER"),
							   rset.getInt("AGE"),
							   rset.getString("EMAIL"),
							   rset.getString("Phone"),
							   rset.getString("ADDRESS"),
							   rset.getString("HOBBY"),
							   rset.getDate("ENROLLDATE"));
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(rset);
			Template.close(stmt);
			
		}
		
		
		return list;
	}

	public Member selectById(Connection conn, String id) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;
	
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("selectById"));
			pstmt.setString(1,id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
							   rset.getString("USERID"),
							   rset.getString("USERPWD"),
							   rset.getString("USERNAME"),
							   rset.getString("GENDER"),
							   rset.getInt("AGE"),
							   rset.getString("EMAIL"),
							   rset.getString("Phone"),
							   rset.getString("ADDRESS"),
							   rset.getString("HOBBY"),
							   rset.getDate("ENROLLDATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(rset);
			Template.close(pstmt);
		}
		return m;
	}

	public ArrayList<Member> selectByName(Connection conn, String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("selectByName"));
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				m = new Member(rset.getInt("USERNO"),
						   rset.getString("USERID"),
						   rset.getString("USERPWD"),
						   rset.getString("USERNAME"),
						   rset.getString("GENDER"),
						   rset.getInt("AGE"),
						   rset.getString("EMAIL"),
						   rset.getString("Phone"),
						   rset.getString("ADDRESS"),
						   rset.getString("HOBBY"),
						   rset.getDate("ENROLLDATE"));
				list.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(rset);
			Template.close(pstmt);
		}
		
		return list;
	}

	public int updateMember(Connection conn, Member m, String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("updateMember"));
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getAddress());
			pstmt.setString(4, m.getHobby());
			pstmt.setString(5, id);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(pstmt);
		}
		
		
		return result;
	}

	public int deleteMember(Connection conn, String deleId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(prop.getProperty("deleteMember"));
			pstmt.setString(1, deleId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Template.close(pstmt);
		}
		
		return result;
	}

}
