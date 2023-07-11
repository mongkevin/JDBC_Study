package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestDML {

	public static void main(String[] args) {
		//sqldeveloper로 데이터베이스에 테이블 STUDENT를 만들어보자
		//컬럼은 SNO,SNAME,SGRADE,SDATE 로 작성하고 NUMBER,VARCHAR2,VARCHAR2,DATE로 구성하시오
		//SNO SEQ_SNO로 시퀀스 생성하여 추가하고 
		//사용자에게  이름을 학년을 입력받아 넣어주고 SDATE SYSDATE로 작성하시오  데이터 3개 추가
		//추가된 데이터 1개를 UPDATE구문으로 변경해보고 
		//1개는 DELETE로 삭제한뒤
		//마지막 최종 남은 데이터를 조회해보시오 
		//switch문으로 메뉴번호 입력받고 추가 수정 삭제 조회 작업 해볼것.

		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		String name = ""; //이름 담을 변수 
		String grade = ""; //학년 담을 변수 
		//추가
		String insertSql = "";//insert문 담을 변수 
		int insertResult = 0; //insert후 처리된 행수 돌려받을 변수 

		//수정 
		String updateSql = ""; //update문 담을 변수 

		//삭제
		String deleteSql = "";



		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			stmt = conn.createStatement();

			while(true) {
				System.out.println("메뉴 번호를 입력해주세요:  ");
				System.out.println("1.추가\n2.수정 \n3.삭제 \n4.조회 \n0.종료");
				int num = sc.nextInt();
				sc.nextLine();

				int result =0;

				switch(num) {
				case 1:
					System.out.println("몇명의 회원을 입력하시겠습니까?");
					int per = sc.nextInt();
					sc.nextLine();
					int fr = 1;
					for(int i=0; i<per; i++) {
						System.out.println("이름 : ");
						name = sc.nextLine();
						System.out.println("학년 : ");
						grade = sc.nextLine();

						insertSql = "INSERT INTO STUDENT "
								+ "VALUES(SEQ_SNO.NEXTVAL,'"+name+"','"+grade+"',SYSDATE)";
						//fr = stmt.executeUpdate(insertSql);
						//1 * 1 * 0 = 0 
						//fr *= fr;// 하나라도 실패하면 0이 곱해지기때문에 실패 여부 확인용도 
						fr *= stmt.executeUpdate(insertSql);
						insertResult+=fr; //몇명이 실행됐는지 확인용도 
						//만약 3명 추가 구문중 2명추가후 마지막추가구문에서 실패하였다면 
					}
					if((fr*insertResult)>0) {
						System.out.println(insertResult+"명이 추가되었습니다.");
						conn.commit();
					}else {
						System.out.println("추가 수행이 실패하였습니다.");
						conn.rollback();
					}
					break; //switch문 벗어나는 break;
				case 2:
					System.out.println("수정할 회원의 이름 : ");
					name = sc.nextLine();
					System.out.println("변경할 정보 :\n1.이름\n2.학년 : ");
					num = sc.nextInt();
					sc.nextLine();
					if(num==1) {
						System.out.println("변경할 이름을 입력하세요.");
						String newName=sc.nextLine();
						updateSql = "UPDATE STUDENT SET SNAME='"+newName+"' "
								+ "WHERE SNAME='"+name+"'";
					}else if(num==2) {
						System.out.println("변경할 학년을 입력하세요.");
						String newGrade=sc.nextLine();
						updateSql = "UPDATE STUDENT SET SGRADE='"+newGrade+"' "
								+ "WHERE SNAME='"+name+"'";
					}else {
						System.out.println("잘못입력하셨습니다.");
					}

					result = stmt.executeUpdate(updateSql);

					if(result>0) {
						System.out.println("수정완료");
						conn.commit();
					}else {
						System.out.println("수정실패");
						conn.rollback();
					}

					break;
				case 3:
					System.out.println("삭제할 회원의 이름 : ");
					name = sc.nextLine();
					deleteSql = "DELETE FROM STUDENT WHERE SNAME='"+name+"'";

					result = stmt.executeUpdate(deleteSql);

					if(result>0) {
						System.out.println(name+" 회원 삭제 완료");
						conn.commit();
					}else {
						System.out.println(name+"에 대한 정보가 없습니다");
					}
					break;	

				case 4:
					rset = stmt.executeQuery("SELECT * FROM STUDENT");

					while(rset.next()) {
						System.out.printf("번호 : %d\n이름 : %s\n학년 : %s\n날짜 : %s\n",
								rset.getInt("SNO")
								,rset.getString("SNAME")
								,rset.getString("SGRADE")
								,rset.getDate(4));
					}
					break;
				case 0: System.out.println("프로그램 종료");return;
				default : System.out.println("잘못 입력하셨습니다. 다시입력해주세요.");
				}

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
				sc.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

}
