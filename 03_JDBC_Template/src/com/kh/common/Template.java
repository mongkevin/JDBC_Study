package com.kh.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Template {

	/*
	 * JDBC 과정 중 반복적으로 쓰이는 구문들을 각각의 메소드로 정의해둘 곳
	 * "재사용할 목적" 으로 공통 템플릿 작업 진행
	 * 
	 * 해당 클래스에서 모든 메소드는 다 static메소드로 작성한다.
	 * -싱글톤 패턴을 이용할 것이기 때문이다
	 * -싱글톤 패턴: 메모리 영역에 한번 객체를 등록시켜 해당 객체를 게속 사용하는 개념 
	 * 
	 * -Connection 생성구문
	 * -close 구문
	 * -commit
	 * -rollback
	 * 등 공통적으로 사용해야하는 메소드를 작성해두고 사용하기
	 * */
	
	private static Connection conn;
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String ID = "JDBC";
	public static final String PWD = "JDBC";
	
	
	//DB와 접속된 Connection 객체 생성 및 반환해주는 메소드 
	public static Connection getConnection() {
		
		try {
			if(conn==null || conn.isClosed()) { //Connection객체가 null인 경우 생성 아닌경우 필드에 등록된 connection 객체 반환
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection(URL,ID,PWD);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
			 
	//전달받은 JDBC용 객체 자원 반납하는 메소드 생성하기
	//Connection 반납
	public static void close(Connection conn) {
		//주의사항- conn이 null일 경우에는 conn.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(conn!=null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//stmt.close
	public static void close(Statement stmt) {
		//주의사항- stmt이 null일 경우에는 stmt.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(stmt!=null&&!stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//rset.close()
	public static void close(ResultSet rset) {
		//주의사항- rset이 null일 경우에는 rset.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(rset!=null&&!rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//commit
	public static void commit(Connection conn) {
			try {
				if(conn!=null && !conn.isClosed()) {
					conn.setAutoCommit(false);
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	//rollback
	public static void rollback(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.setAutoCommit(false);
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}














