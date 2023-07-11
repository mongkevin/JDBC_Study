package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberView {

	private Scanner sc = new Scanner(System.in);
	
	private MemberController mc = new MemberController();
	
	public void mainMenu() {
		System.out.println("************회원 관리 프로그램************");
		System.out.println("1.회원 추가");
		System.out.println("2.회원 아이디로 검색");
		System.out.println("3.회원 이름으로 검색");
		System.out.println("4.회원 정보 변경");
		System.out.println("5.회원 전체 조회");
		System.out.println("6.회원 탈퇴");
		System.out.println("0.프로그램 종료");
		System.out.println("====================");
		System.out.println("이용하실 메뉴 선택해주세요: ");
		int menu = sc.nextInt();
		sc.nextLine();
		
		switch(menu) {
		case(1): insertMember(); break;
		case(2): selectById(); break;
		case(3): selectByName(); break;
		case(4): updateMember(); break;
		case(5): selectAll(); break;
		case(6): deleteMember(); break;
		case(0): System.out.println("프로그램 종료"); return;
		default: System.out.println("잘못입력하셨습니다.");
		}
		
	}
	
	//회원 추가 메소드
	public void insertMember() {
		System.out.println("----회원 추가----");
		//입력
		System.out.println("아이디: ");
		String userId = sc.nextLine();
		
		System.out.println("비밀번호: ");
		String userPwd = sc.nextLine();
		
		System.out.println("이름: ");
		String userName = sc.nextLine();
		
		System.out.println("성별(M/F): ");
		String gender = String.valueOf(sc.nextLine().toUpperCase().charAt(0));
		
		System.out.println("나이: ");
		int age = sc.nextInt();
		sc.nextLine();
		
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		System.out.println("핸드폰 번호(번호만): ");
		String phone = sc.nextLine();
		
		System.out.println("주소: ");
		String address = sc.nextLine();
		
		System.out.println("취미(,로 공백없이 나열): ");
		String hobby = sc.nextLine();
		
		//입력받은 정보를 controller에게 전달(회원추가 요청)
		mc.insertMember(userId, userPwd, userName, gender, age, email, phone, address, hobby );
	}
	
	//회원 이름으로 검색 메소드
	public void selectById() {
		System.out.println("----아이디로 회원 검색----");
		System.out.println("검색하실 아이디 입력: ");
		String Id = sc.nextLine();
		
		mc.selectById(Id);
	}
	
	//회원 아이디로 검색 메소드 
	public void selectByName() {
		System.out.println("----이름으로 회원 검색----");
		System.out.println("검색하실 이름 입력: ");
		String name = sc.nextLine();
		
		mc.selectByName(name);
	}
	
	//회원 추가 메소드
	public void updateMember() {
		System.out.println("----회원 정보 수정----");
		System.out.println("수정하실 아이디를 입력: ");
		String id = sc.nextLine();
		Member m = mc.selectById(id);
		
		if(m!=null) {
			System.out.println("변경할 비밀번호");
			String newPwd = sc.nextLine();
			System.out.println("변경할 이메일: ");
			String newEmail = sc.nextLine();
			System.out.println("변경할 휴대폰번호(-없이): ");
			String newPhone = sc.nextLine();
			System.out.println("변경할 주소: ");
			String newAddress = sc.nextLine();
			
			mc.updateMember(newPwd,newEmail,newPhone,newAddress);
		}else {
			System.out.println(id + "가 없습니다.");
		}
		
	}
	
	//전체 조회 메소드
	public void selectAll() {
		System.out.println("----회원 정보 전체 조회----");
		
		mc.selectAll();
	}
	
	//회원 탈퇴 메소드
	public void deleteMember() {
		System.out.println("----회원 정보 전체 조회----");
		System.out.println("삭제하실 회원의 아이디 입력: ");
		String id = sc.nextLine();
		Member m = mc.selectById(id);
		
		if(m!=null) {
			mc.deleteMember(id);
		}else {
			System.out.println(id+"없는 아이디입니다.");
		}
		
	}

	//==================사용자가 성공 실패 여부 확인할 뷰=======================
	public void displaySuccess(String message) {
		System.out.println("서비스 요청 성공 \n"+message);
	}

	public void displayFail(String message) {
		System.out.println("서비스 요청 실패 \n"+message);
	}

	

	public void displayOne(Member m) {
		System.out.println("서비스 요청 성공 \n"+m);
	}

	public void displayList(ArrayList<Member> list) {
		System.out.println("조회된 결과는 "+ list.size() + "건 입니다.");
		
		for(Member m : list) {
			System.out.println(m.toString());
		}
		
	}
	
}
