package com.kh.run;

import com.kh.view.MemberView;

public class MemberRun {
	public static void main(String[] args) {
	
		new MemberView().mainMenu();
		
	/*
	 * Properties: Map계열의 컬렉션 key + value 세트로 담는게 특징
	 * Properties는 주로 외부 설정파일을 읽어오기 또는 파일형태로 출력하기 위해 사용된다.
	 * 
	 * properties, xml파일로 내보내기 - store(), storeToXML()
	 * 
	 * */
	//resources 폴더 만들기
	
//		File f = new File("resources");
//		f.mkdir();	
		
		//프로퍼티스 값 세팅
//		Properties prop = new Properties();
//		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
//		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
//		prop.setProperty("username", "JDBC");
//		prop.setProperty("password", "JDBC");
		
//		System.out.println(prop);
		
//		-------------------driver.properties 파일 생성-------------------
//		try {
//			prop.store(new FileOutputStream("resources/driver.properties"),"driver properties");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	 
		
//		-------------------QUERY.XML 파일 생성-------------------
//		prop.setProperty("insertMember", "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)");
//		
//		try {
//			prop.storeToXML(new FileOutputStream("resources/query.xml"), "query");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
