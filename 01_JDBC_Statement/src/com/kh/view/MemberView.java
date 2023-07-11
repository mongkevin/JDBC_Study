package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

//사용자가 보게 될 화면
public class MemberView {
	//전역으로 쓸수 있는 Scanner 객체 생성
	private Scanner sc = new Scanner(System.in);
	
	//전역으로 쓸수 있는 MemberController 객체 생성
	private MemberController mc = new MemberController();
	
	//사용자가 처음 보게될 메인 메뉴
	public void mainMenu() {
		
		while(true) {
			System.out.println("******회원 관리 프로그램*******");
			System.out.println("1.회원 추가");
			System.out.println("2.회원 전체 조회");
			System.out.println("3.화원 아이디로 검색");
			System.out.println("4.회원 이름 검색");
			System.out.println("5.회원 정보 수정");
			System.out.println("6.회원 탈퇴");
			System.out.println("0.프로그램 종료");
			System.out.println("===========================");
			System.out.println("이용할 메뉴 번호를 입력해주세요.");
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
			default: System.out.println("잘못된 메뉴번호를 입력하셨습니다. 다시 입력해주세요.");
				
			
			}
			
		}
	}

	public void deleteMember() {
		System.out.println("-------회원 탈퇴-------");
		System.out.println("탈퇴할 회원의 ID: ");
		String userId = sc.nextLine();
		
		mc.deleteMember(userId);
	}

	public void updateMember() {
		System.out.println("-------회원 정보 변경-------");
		
		//변경할 회원의 아이디
		System.out.println("변경하실 아이디를 입력해주세요: ");
		String userId = sc.nextLine();
		//변경할 아이디를 잘입력했는지 먼저 조회하고 있을 경우 변경내용들 입력받기
		Member m = mc.selectById(userId);
		
		if(m!=null) {
			//변경할 정보들
			System.out.println("변경할 비밀번호");
			String newPwd = sc.nextLine();
			System.out.println("변경할 이메일: ");
			String newEmail = sc.nextLine();
			System.out.println("변경할 휴대폰번호(-없이): ");
			String newPhone = sc.nextLine();
			System.out.println("변경할 주소: ");
			String newAddress = sc.nextLine();
			
			//회원 정보 수정 요청
			mc.updateMember(userId, newPwd, newEmail, newPhone, newAddress);
			
		}else { //조회된 아이디가 없는 경우 메인으로 돌아가기
			return;
		}
		
	}

	public void selectByName() {
		System.out.println("-------이름으로 검색-------");
		System.out.println("검색할 회원의 이름: ");
		String name = sc.nextLine();
		mc.selectByName(name);
	}

	public void selectById() {
		System.out.println("-------회원 아이디로 검색-------");
		System.out.println("검색할 회원의 아이디:");
		String userId = sc.nextLine();
		
		mc.selectById(userId);
	}

	//전체 회원 조회 화면
	public void selectAll() {
		System.out.println("-------회원 전체 조회-------");
		
		//회원 전체 조회 요청 - DB에 등록되어 있는 회원 정보들을 다 조회해와서 출력해줘
		mc.selectAll();
	}

	//회원추가용 화면
	//추가하고자하는 회원 정보를 입력받아서 추가 요청을 할수 있는 화면
	public void insertMember() {
		System.out.println("-------회원추가-------");
		
		//입력
		System.out.println("아이디: ");
		String userId = sc.nextLine();
		System.out.println("비밀번호: ");
		String userPwd = sc.nextLine();
		System.out.println("이름: ");
		String userName = sc.nextLine();
		System.out.println("나이: ");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.println("성별(M/F): ");
		String gender = String.valueOf(sc.nextLine().toUpperCase().charAt(0));
		System.out.println("이메일: ");
		String email = sc.nextLine();
		System.out.println("핸드폰 번호: ");
		String phone = sc.nextLine();
		System.out.println("주소: ");
		String address = sc.nextLine();
		System.out.println("취미(,로 공백없이 나열): ");
		String hobby = sc.nextLine();
		
		//입력받은 정보를 넘겨 회원 추가 요청-> Controller에 요청보내기
		mc.insertMember(userId,userPwd,userName,gender,age,email,phone,address,hobby);
	}

	
	
//============처리 후 사용자가 보게될 응답화면==================
	
	//서비스 요청 성공 시 보게될 화면
	public void displaySuccess(String message) {

		System.out.println("서비스 요청 성공: "+ message);
	}

	//서비스 요청 실패시 보게될 화면
	public void displayFail(String message) {
		
		System.out.println("서비스 요청 실패: "+ message);
	}

	//조회 결과 없을때 보게될 화면 
	public void displayNodata(String message) {
		System.out.println(message);
		
	}

	//조회결과가 있을때 보게될 화면
	public void displayList(ArrayList<Member> list) {
		System.out.println("조회된 결과는 "+ list.size() + "건 입니다.");
		
		for(Member m : list) {
			System.out.println(m.toString());
		}
	}

	//조회결과가 하나만 있을때 보게될 화면
	public void displayOne(Member m) {
		System.err.println("조회된 결과는 ");
		System.out.println(m);
	}

	
}








