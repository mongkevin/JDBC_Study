package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

public class MemberController {

	//회원 추가 메소드
	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		//1.사용자가 입력한 값들을 가공처리하여 전달하기
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
		
		//2.Service의 InserMember 메소드 호출하기
		int result = new MemberService().insertMember(m);
		
		//3.결과값에 따라서 사용자에게 보여줄 화면 지정하기
		if(result>0) {
			new MemberView().displaySuccess("회원 추가 성공");
		}else {
			new MemberView().displayFail("회원 추가 실패");
		}
	}

	public void SelectAll() {
		ArrayList<Member> list = new MemberService().SelectAll();
		
		if(list.isEmpty()) {
			new MemberView().displayFail("전체 조회 실패");
		}else {
			new MemberView().displaySuccess("회원 추가 성공");
			new MemberView().displayList(list);
		}
		
		
	}

	public Member selectById(String id) {
		Member m = null;
		
		m = new MemberService().selectById(id);
		
		if(m != null) {
			new MemberView().displaySuccess("회원 조회 성공");
			new MemberView().displayMember(m);
		}else {
			new MemberView().displayFail("회원 조회 실패");
		}
		
		return m;
	}

	public void selectByName(String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		
		list = new MemberService().selectByName(name);
		
		if(list.isEmpty()) {
			new MemberView().displayFail("전체 조회 실패");
		}else {
			new MemberView().displaySuccess("회원 추가 성공");
			new MemberView().displayList(list);
		}
	}

	public void updateMember(String id, String newPwd, String newEmail, String newPhone, String newAddress, String newHobby) {
		int result = 0;
		
		result = new MemberService().updateMember(id, newPwd, newEmail, newPhone, newAddress, newHobby);
		
		if(result>0) {
			new MemberView().displaySuccess("회원 수정 성공");
		}else {
			new MemberView().displayFail("전체 수정 실패");
		}
	}

	public void deleteMember(String deleId) {
		int result = 0;
		result = new MemberService().deleteMember(deleId);
		
		if(result>0) {
			new MemberView().displaySuccess("회원 탈퇴 성공");
		}else {
			new MemberView().displayFail("전체 탈퇴 실패");
		}
		
	}

}
