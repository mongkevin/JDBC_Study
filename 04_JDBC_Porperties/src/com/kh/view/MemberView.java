package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberView {
	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController();
	
	
	public void mainMenu() {
		
		while(true) {
			System.out.println("회원관리 프로그램");
			System.out.println("1.회원추가");
			System.out.println("2.회원 전체 조회");
			System.out.println("3.회원 아이디로 조회");
			System.out.println("4.회원 이름으로 조회");
			System.out.println("5.회원 정보 수정");
			System.out.println("6.회원 탈퇴");
			System.out.println("0.프로그램 종료");
			
			System.out.println("메뉴 번호 선택:");
			int num = sc.nextInt();
			sc.nextLine();
			
			switch(num) {
			case 1: insertMember(); break;
			case 2: selectAll(); break;
			case 3: selectById(); break;
			case 4: selectByName(); break;
			case 5: updateMember(); break;
			case 6: deleteMember(); break;
			case 0: System.out.println("프로그램 종료"); return;
				default: System.out.println("잘못 입력하셨습니다.");
			}
		}
	}

	//회원 추가
	public void insertMember() {
		System.out.println("====회원 추가====");
		System.out.println("아이디: ");
		String userId = sc.nextLine();
		System.out.println("비밀번호: ");
		String userPwd = sc.nextLine();
		System.out.println("이름: ");
		String userName = sc.nextLine();
		System.out.println("성별 (M/F): ");
		String gender = String.valueOf(sc.nextLine().toUpperCase().charAt(0));
		System.out.println("나이: ");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		System.out.println("핸드폰 번호 (-없이): ");
		String phone = sc.nextLine();
		System.out.println("주소: ");
		String address = sc.nextLine();
		System.out.println("취미 (,로 나열 공백없이): ");
		String hobby = sc.nextLine();
		
		//MemberController에게 입력받은 데이터 전달하기(회원추가 요청)
		mc.insertMember(userId,userPwd,userName,gender,age,email,phone,address,hobby);
	}

	//회원 전체 조회
	public void selectAll() {
		System.out.println("====회원 전체 조회====");
		mc.selectAll();
	}
	
	//회원 아이디로 검색
	public void selectById() {
		System.out.println("====회원 아이디 검색====");
		System.out.println("검색하실 회원의 아이디 입력: ");
		String id = sc.nextLine();
		
		mc.selectById(id);
	}
	
	//회원 이름으로 조회
	public void selectByName() {
		System.out.println("====회원 이름 검색====");
		System.out.println("검색하실 회원의 이름 입력: ");
		String name = sc.nextLine();
		
		mc.selectByName(name);
		
	}
	
	//회원 정보 수정
	public void updateMember() {
		System.out.println("====회원 정보 수정====");
		System.out.println("업데이트 하실 회원의 아이디 입력: ");
		String id = sc.nextLine();
		Member m = mc.selectById(id);
		
		if(m != null) {
			
			System.out.println("바꿀 정보를 선택하세요");
			System.out.println("1.비밀번호:");
			System.out.println("2.이메일: ");
			System.out.println("3.주소: ");
			System.out.println("4.취미: ");
			int num = sc.nextInt();
			sc.nextLine();
			
			switch(num){
			case 1:
				System.out.println("바꿀 비밀번호 입력: ");
				String newPwd = sc.nextLine();
				m.setUserPwd(newPwd);
				break;
			case 2:
				System.out.println("바꿀 이메일 입력: ");
				String newEmail = sc.nextLine();
				m.setEmail(newEmail);
				break;
			case 3:
				System.out.println("바꿀 주소 입력: ");
				String newAddress = sc.nextLine();
				m.setAddress(newAddress);
				break;
			case 4:
				System.out.println("새로운 취미: ");
				String newHobby = sc.nextLine();
				System.out.println("1.취미 추가하기");
				System.out.println("2.취미 바꾸기");
				int num2 = sc.nextInt();
				sc.nextLine();
				if(num == 1) {
					m.setHobby(m.getHobby()+","+newHobby);
				}else if(num == 2) {
					m.setHobby(newHobby);
				}else {
					System.out.println("잘못입력하셨습니다.");
					return;
				}
				break;		
			default:System.out.println("잘못입력했습니다.");
				
			}
		}else {
			displayFail("입력하신"+id+"가 없습니다.");
		}
			
		mc.updateMember(m, id);
	}
		
	
	
	public void deleteMember() {
		System.out.println("====회원 탈퇴====");
		System.out.println("탈퇴하실 아이디 입력: ");
		String deleId = sc.nextLine();
		Member m = mc.selectById(deleId);
		
		if(m != null) {
			mc.deleteMember(deleId);
		}else {
			System.out.println("입력하신"+deleId+"가 없습니다.");
		}
	}









//==========================화면========================
	//요청 성공시 보게될 화면
	public void displaySuccess (String message) {
		System.out.println("-요청하신 작업-\n"+message);
	}
	//요청 실패시 보게될 화면
	public void displayFail (String message){
		System.out.println("-요청하신 작업-\n"+message);
	}

	public void displayList(ArrayList<Member> list) {
		System.out.println("조회된 건수는"+list.size()+"건 입니다.");
		
		for(Member m: list) {
			System.out.println(m);
		}
	}

	public void displayMember(Member m) {
		System.out.println("조회하신 회원 정보: \n"+m);
	}
	
}
