package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Template {

	public static Connection getConnection() {
		/*
		 * 기본방식: JDBC Driver 구문, 내가 접속할 DB의 url 정보, 계정명, 비밀번호들을
		 * 		   자바코드 내에 작성함 - 정적코딩방식
		 * -문제점: DBMS가 변경되었을 경우 또는 접속할 url/계정명/비밀번호등이 변경되었을 경우
		 * 		  자바 소스코드를 수정해야한다(수정사항 적용하려면 프로그램 재가동 하여야함) 
		 * 		  -유지보수 불편함
		 * 
		 * -해결방식: DB관련한 정보들을 별도로 관리하는 외부 파일을 만들어 관리
		 * 			외부파일로 key에 대한 value를 읽어 반영하자
		 * 해당 구문이 실행되는 시점에 외부 파일을 읽어들여 사용하기 때문에 수정사항 바로 적용 가능
		 * */
		//파일정보 읽어줄 Properties 객체 생성
		Properties prop = new Properties();
		
		Connection conn = null;
		
		try {
			//파일 정보 읽어오기
			prop.load(new FileInputStream("resources/driver.properties"));
			
			//prop으로부터 getProperty를 사용하여 각 키에 해당하는 벨류를 꺼내오기
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"),
											   prop.getProperty("password"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
	
	public static void close(Connection conn) {
		
		try {
			if(conn!=null && !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		
		try {
			if(rset!=null && !rset.isClosed()) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
