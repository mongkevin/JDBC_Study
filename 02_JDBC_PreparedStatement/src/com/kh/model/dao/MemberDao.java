package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

public class MemberDao {

	/*
	 * Statement 와 PreparedStatement의 차이점
	 * 관계는 Statement가 부모타입 PreparedStatement는 자식타입
	 * 1) Statement는 완성된 SQL문을 전달, PreparedStatement 미완성된 SQL을 전달
	 * 2) Statement 객체 생성시 stmt = conn.createStatement();
	 *    PreparedStatement객체 생성시 pstmt = conn.prepareStatement(sql);
	 *    
	 * 3) Statement로 SQL문 실행시 결과 - stmt.executeXXX();
	 *    PreparedStatement로 SQL문 실행시 ?로 표현한 빈공간을 채워줘야한다.
	 *    pstmt.setString(?위치,값);
	 *    pstmt.setInt(?위치, 값);
	 *    결과 - pstmt.executeXXX();
	 * */
	public int insertMember(Member m) {
		int result = 0; //결과 처리 행수 담을 변수
		Connection conn = null; //연결객체
//		Statement stmt = null; //기존사용방식
		PreparedStatement pstmt = null; //위치 홀더를 이용할 수 있는 객체방식
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE )";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			//1)드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection 객체 생성
			conn = DriverManager.getConnection(url,uId,uPwd);
			conn.setAutoCommit(false);
			//3)PreparedStatement 객체 생성(미완성 상태인 sql문을 먼저 전달하며 생성)
			pstmt = conn.prepareStatement(sql);
			//4)미완성 SQL문일 경우에 위치홀더에 데이터 넣어서 완성시켜주기
			//pstmt.setXXX(?위치, 값)
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			//5)완성된 SQL문 전달 후 결과값 받기
			result = pstmt.executeUpdate();
			
			//6)트랜잭션 처리
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public Member selectById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;
		
		String sql = "SELECT * FROM MEMBER WHERE USERID = ?";
				
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String uId = "JDBC";
		String uPwd = "JDBC";
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); 
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member();
				
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrolldate(rset.getDate("ENROLLDATE"));
						
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public ArrayList<Member> selectByName(String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//방법 1) ? 위치 홀더가 문자열처리 되기 때문에 형식 오류가 발생한다 ex)'%'홍길동'%'
		//oracle에서 문자열 덧셈연산을 사용하여 하나의 문자열로 만들기
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'";
		
		//방법2) 위치홀더를 마련해놓고 자바에서 %%기호까지 추가하여 대입하기
		//"%홍길동%"로 만들어서 위치홀더에 대입
//		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
//		pstmt.setString(1, "%"+name+"%");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Member m =  new Member(rset.getInt("USERNO"),
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
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
	}

	public int updateMember(Member m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql =  "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			result = pstmt.executeUpdate();
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
		return result;
	}

	public ArrayList<Member> selectAll() {
		ArrayList<Member> list = new ArrayList<Member>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = "SELECT * FROM MEMBER";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				Member m = new Member();
				m.setUserNo(rset.getInt("USERNO"));
				m.setUserId(rset.getString("USERID"));
				m.setUserPwd(rset.getString("USERPWD"));
				m.setUserName(rset.getString("USERNAME"));
				m.setGender(rset.getString("GENDER"));
				m.setAge(rset.getInt("AGE"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrolldate(rset.getDate("ENROLLDATE"));
				list.add(m);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
			
		return list;
	}

	public int deleteMember(String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID = ?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, uId, uPwd);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			result = pstmt.executeUpdate();
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return result;
	}

}
