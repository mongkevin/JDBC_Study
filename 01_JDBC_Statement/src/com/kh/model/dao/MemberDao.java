package com.kh.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Member;

//DAO(DATA ACCESS OBJECT)

public class MemberDao {
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
	 * 8) 돌려받은 결과를 Controller로 반환한다
	 * -SELECT 결과라면 객체 또는 결과값이 넘어간다.
	 * -DML문이라면 처리된 행의 값(int 값) 넘어간다.
	 * 
	 * */
	
	//사용자가 회원 추가시 입력했던 값들을 DB에 저장하는 메소드 
	public int insertMember(Member m) {
		
		//필요한 변수들 세팅
		int result = 0; //처리된 결과(처리된 행의 수) 받을 변수
		Connection conn = null; //접속한 DB 정보 담고 있는 변수
		Statement stmt = null; // sql 전달 및 결과 받아줄 변수
		
		//SQL문 
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,'"+m.getUserId()+"','"
				+ m.getUserPwd() +"','"+m.getUserName()+"','"+m.getGender()+"',"+m.getAge()+
				",'"+m.getEmail()+"','"+m.getPhone()+"','"+m.getAddress()+"','"+m.getHobby()
				+"', SYSDATE)";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			//1)JDBC 드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2)Connection 객체 생성(DB와 연결 - 연결정보(url), 계정명, 계정비빌번호)
			conn = DriverManager.getConnection(url, uId, uPwd);
			conn.setAutoCommit(false);
			//3)Statement 객체 생성(sql문 전달 및 결과 받기 위한 용도)
			stmt = conn.createStatement();
			
			//4)SQL 전달 및 결과 받기 (INSERT 문( -DML 구문이라 executeUpdate()메소드 사용
			result = stmt.executeUpdate(sql); //sql문을 전달하여 실행시키고 결과값 돌려답아 result에 담기
			
			//5)전달받은 결과 값을 토대로 느랜잭션(확성, 취소)
			if(result>0) { //1개 이사의 행이 insert되었다면? 성공 - commit;
				conn.commit();
			}else {//실패
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//사용을 마친 jdbc객체 자원들 반납하기-- 연관있는 자원들 나중에 생성된것이 먼저 반납되어야한다.
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//결과 반환
		return result; //dml(insert)이 처리된 결과 행 수 
		
	}

	//등록된 멤버 정보 전체 조회하는 메소드
	public ArrayList<Member> selectAll() {
		
		
		//필요한 준비물 세팅 (변수들)
		//조회된 회원 정보들을 담아줄 리스트 생성 ArrayList
		ArrayList<Member> list = new ArrayList<>(); //비어있는 리스트 준비
		//finally 구문에서 자원 반납하기 위해서 try문 밖에 미리 선언해 놓는 것
		Connection conn = null; //접속 DB 정보
		Statement stmt = null; //SQL문 실행 및 결과받기
		ResultSet rset = null; //SELECT 결과 받아올 객체
		
		String sql = "SELECT * FROM MEMBER";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			//1)드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection 객체 생성
			conn = DriverManager.getConnection(url,uId,uPwd);
			//3)Statement 객체 생성
			stmt = conn.createStatement();
			
			//4)SQL문 전달하고 결과값 받기 (ResultSet)
			rset = stmt.executeQuery(sql);
			
			//5)조회된 결과가 담겨있는 ResultSet을 이용하여 다음 행에 데이터가 있는 확인하고
			//  있다면 List에 Member객체 형태로 한 행씩 담아주기
			///rset.next() : 커서를 한 줄 아래로 내려서 해당 행이 존재하면 true반환 아님 false
			while(rset.next()) {
				//현재 rset의 커서가 가르키고 있는 해당 행에 데이터를 
				//하나씩 추출하여 Member객체에 담는다
				Member m = new Member();
				
				//rset 으로부터 어떠한 컬럼에 해당하는 값을 추출할 것인지 제시
				//-컬럼명(대소문자X),컬럼순번
				//권장사항 - 컬럼명(대문자)
				//rset.getInt(컬럼명 또는 순번): number형타입을 꺼낼때
				//rset.getString(컬럼명 또는 순번): 문자타입을 꺼낼때
				//rset.getDate(컬럼명 또는 순번): Date형을 꺼낼때
				
				//USERNO를 추출하여 Member객체에 담으려면?
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
				//한 행에 대한 데이터를 전부 Member객체에 담기 완료
				//담아준 데이터 Member객체를 list에 추가하기
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
				//자원반납
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return list; //조회결과 담긴 list반환하기
	}

	//회원 아이디로 검색 메소드
	public Member selectById(String userId) {//SELECT 절 
		
		//아이디는 유니크 제약조건이 걸려있기 때문에 조회된 결과는 하나 또는 0개가 나올것
		//한 회원의 정보를 담아갈 Member객체변수 준비
		Member m = null;
		
		//필요한 변수들
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		//문자열 변수를 더해넣으면 값 자체가 들어가기 때문에 오라클에서 문자열처리를 위한 ''로 감싸주는 작업 필요
		String sql = "SELECT * FROM MEMBER WHERE USERID = '"+userId+"'";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);

			if(rset.next()) {//아이디는 중복 불가이기 때문에 반복적으로 행을 찾을 필요가 없으니 if무능로 한번 조건처리
				//조회결과가 있을때(member에 대한 정보가 한 행 있을때
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
				//set메소드로 하는 방법
//				m = new Member();
//				m.setUserNo(rset.getInt("USERNO"));
				
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
		return m; //조회결과가 담긴 member객체주소 보내기(조회겨로가가 없다면 null을 반환한다.)
	}

	//이름을 통한 조회 메소드
	public ArrayList<Member> selectByName(String name) {
		//조회결과 담아둘 list준비
		ArrayList<Member> list = new ArrayList<>();
		
		//연결정보 담을 객체변수
		Connection conn = null;
		//sql문 전달 및 결과 받을 객체변수
		Statement stmt = null;
		//select문 조회결과 받을 결과객체
		ResultSet rset = null;
		
		//SQL문 담을 변수(2글자를 입력해도 이름 조회가 가능하게 작업하기
//		String sql = "SELECT * FROM WHERE USERNAME = '"+name+"'";
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%"+name+"%'";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {//조회결과가 있다면true/없다면false
				//만약 조회된 겨로가가 있다면 member객체에 담아서 list에 담아 옮겨주기
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
		} finally {
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

	//회원 정보 수정 메소드
	public int updateMember(Member m) {
		
		//DML구문이기 때문에 처리된 행수 돌려받을 변수 설정
		int result = 0;
		
		//연결정보
		Connection conn = null;
		//sql문 전달 및 결과값 받을 객체
		Statement stmt = null;
		
		//SQL
		//UPDATE MEMBER SET SUERPWD = '',EMAIL='',PHONE='',ADDRESS='' WHERE USERID ='';
		
		String sql = "UPDATE MEMBER SET USERPWD='"+m.getUserPwd()+"',"+
		                                     "Email='"+m.getEmail()+"',"+
		                                     "Phone='"+m.getPhone()+"',"+
		                                     "ADDRESS='"+m.getAddress()+"'"+
		                                     "WHERE USERID='"+m.getUserId()+"'";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
			if(result>0) { //성공 시
				conn.commit();
			}else { //실패 시
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
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
		return result;
	}

	//회원 탈퇴 요청 메소드
	public int deleteMember(String userId) {
		int result = 0;
		
		Connection conn = null;
		Statement stmt = null;
		
		String sql = "DELETE FROM MEMBER WHERE USERID ='"+userId+"'";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC"; //계정명
		String uPwd = "JDBC"; //비빌번호
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
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
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
		return result;
	}

}








