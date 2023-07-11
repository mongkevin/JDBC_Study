package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestRun {

	public static void main(String[] args) {
		
		/*
		 * JDBC용 객체 
		 * -Connection: DB의 연결정보를 담고있는 객체(IP주소, PORT번호, 계정명, 비밀번호)
		 * -(Prepared)Statement: 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아내는 객체
		 * -ResultSet: 만일 실행한 SQL문의 SELECT문일 경우 조회된 결과들이 담겨있는 객체 
		 * 
		 * JDBC 처리 순서
		 * 1) JDBC Driver 등록: 해당 DBMS가 제공하는 클래스 등록
		 * 2) Connection 생성: 접속하고자 하는 DB에 정보를 입력하여 DB에 접속하여 생성 
		 * 3) Statement 생성: Connection 객체를 이용하여 생성 
		 * 4) SQL문을 전달하면서 실행: Statement 객체를 이용해서 SQL문을 실행
		 *    -SELECT문일 경우 excuteQuery(); 메소드를 사용
		 *    -나머지 DML문일 경우 excuteUpdate(); 메소드 사용 
		 *    
		 * 5)결과 받기
		 * 6_1) SELECT문 - ResultSet 객체(조회된 데이터가 담겨있다)로 받아준다.
		 * 6_2) SELECT문으로 실행하여 조회된 결과를 담은 ResultSet에 있는 데이터를 추출하여 VO객체에 담기
		 *      만약 여러행이 조회된다면? ArrayList에 묶어서 담아가기
		 * 6_3) DML문 - int형 변수로 처리된 행수 전달받기
		 * 6_4) DML문이 실행됐다면 트랜잭션 처리를 해주어야한다. (성공이면 commit; 실패면 rollback;)
		 * 7) 사용 완료한 JDBC용 객체들 자원 반납(close) -> 생성의 역순으로 반납한다.
		 * 
		 * */
		
		//INSERT문 -> 처리된 행수(int)
		//TEST테이블에 테이터 INSERT해보기 - INSERT INTO TEST VALUES(1,'김디비',SYSDATE)
		
		//자원 반납을 위해 미리 객체 선언만 해두기
		Connection conn = null; //DB접속 정보를 담고 있는 객체
		Statement stmt = null; //sql문 전달하여 실행 후 결과 돌려주는 객체
		int result = 0; //처리된 결과 행수 전달 받을 변수
		String sql = "INSERT INTO TEST VALUES(2,'김박사',SYSDATE)"; //처리할 sql문
		
		try {
			//1) jdbc driver 등록(oracle.jdbd.driver.OracleDriver)
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//ClassNotFoundException
//		    System.out.println("jdbc driver 등록 성공");
			
			//2)Connection 객체 생성: DB에 연결(url,계정명,비밀번호)
			//-ip주소1521(포트번호)xe(버전)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
		    
			//자동커밋여부 설정 -- 직접 트랜잭션 처리를 하겠다.
			conn.setAutoCommit(false); //true 값이 기본값으로 자동으로 커밋이 된다.
		    
			//3)Statement 객체 생성
		    stmt = conn.createStatement();
		    
		    //4)sql문을 전달하고 결과값 받기(처리된 행수)
		    result = stmt.executeUpdate(sql);
		    
		    //5)트랜잭션 처리하기 (성공 실패 여부 확인 후 commit/rollback)
		    if(result>0) { //성공했을 경우 처리된 행수가 1이상인 경우 (commit)
		    	conn.commit();
		    }else { //실패했을 경우 되돌리기(rollback)
		    	conn.rollback();
		    }
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//6)자원 반납하기
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(result>0){
			System.out.println("성공적으로 데이터가 추가되었습니다.");
		}else {
			System.out.println("데이터가 추가에 실패하였습니다.");
		}
		
	}

}





















