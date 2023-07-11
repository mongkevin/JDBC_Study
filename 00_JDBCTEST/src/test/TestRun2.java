package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestRun2 {

	public static void main(String[] args) {
		//방금 진행했던 insert문을 진행하는데 insert할 데이터를 사용자에게 scanner 입력받아 넣어보기
		//번호와 이름을 입력 받고 날짜는 sysdate로 진행할것
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		//INSERT INTO TEST VALUES(1,'김디비',SYSDATE)
		System.out.println("번호를 입력하세요.");
		int num = sc.nextInt();
		sc.nextLine();
		System.out.println("이름을 입력하세요.");
		String name = sc.nextLine();
		
		//홑따옴표를 추가해줘야한다. 변수가 문자 그대로 들어가버리기 때문에 별도의 오라클 전용 문자열 처리작업을 해야함
		String sql = "INSERT INTO TEST VALUES("+num+",'"+name+"',SYSDATE)";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
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
		}finally{
			try {
				stmt.close();
				conn.close();
				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(result>0) {
			System.out.println("성공적으로 데이터를 추가하였습니다.");
		}else {
			System.out.println("데이터를 추가하는데 실패했습니다.");
		}
		
	}

}
