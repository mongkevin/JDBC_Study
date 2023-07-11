package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSelect {

	public static void main(String[] args) {
		//TEST 테이블에 있는 데이터 조회해오기
		//SELECT문 - ResultSet(조회된 행객체가 담겨온다)
		
		//필요한 변수들 세팅하기
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null; //결과행 담을 객체 
		
		String sql = "SELECT * FROM TEST";
		
		try {
			//1)드라이버 등록 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2)Connection 객체 생성
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
			//3)Statement 객체 생성
			stmt = conn.createStatement();
			
			//4)sql문 전달 및 결과값 받기 select문의 결과는 ResultSet에 담겨져 온다
			//select문을 전달하는 메소드는 executeQuery()이다
			rset = stmt.executeQuery(sql);
			
			//5)ResultSet에 담겨져 있는 데이터를 꺼내보자
			//현재 참조하고 있는 rset으로부터 어떤 컬럼에 해당하는 값을 어떤 타입에 담을 것인지 제시
			//db에 컬럼명을 제시하면 된다
			
			
			while(rset.next()) {//rset.next(): 커서 위치 옮기기 - 옮긴 후 대상이 있으면 true 없으면 false
				int tno = rset.getInt("TNO"); //TNO에 있는 데이터를 자바변수tno에 담겠다.
				String tname = rset.getString("TNAME");
				Date tdate = rset.getDate("TDATE"); //java.sql 패키지에 있는 Date
				
				System.out.println(tno + " " + tname + " " + tdate);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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
		
		
	}

}
