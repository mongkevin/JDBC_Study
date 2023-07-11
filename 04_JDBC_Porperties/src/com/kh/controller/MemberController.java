package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

public class MemberController {

	private MemberService service = new MemberService();
	
	
	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		//가공처리
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address, hobby);
	
		//Service에 전달
		int result = service.insertMember(m);
		
		//성공 실패 여부 확인 후 뷰 페이지 선택해주기
		if(result>0) {
			new MemberView().displaySuccess("회원 추가 성공");
		}else {
			new MemberView().displayFail("회원 추가 실패");
		}
	}


	public void selectAll() {

		ArrayList<Member> list = new ArrayList<Member>();
		
		list = service.selectAll();
		
		if(list.isEmpty()) {
			new MemberView().displayFail("전체회원 조회 실패");
		}else {
			new MemberView().displaySuccess("전체회원 조회 성공");
			new MemberView().displayList(list);
		}
	}


	public Member selectById(String id) {
		Member m = null;
		m = service.selectById(id);
		
		if(m!=null) {
			new MemberView().displaySuccess("회원 조회 성공");
			new MemberView().displayMember(m);
		}else {
			new MemberView().displayFail("회원 조회 실패");
		}
		return m;
	}


	public void selectByName(String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		list = service.selectByName(name);
		
		if(list.isEmpty()) {
			new MemberView().displayFail("회원 조회 실패");
		}else {
			new MemberView().displaySuccess("회원 조회 성공");
			new MemberView().displayList(list);
		}
		
	}


	public void updateMember(Member m,String id) {
		int result = 0;
		result = service.updateMember(m,id);
		
		if(result>0) {
			new MemberView().displaySuccess("회원 정보 수정 성공");
			new MemberView().displayMember(m);
		}else {
			new MemberView().displayFail("회원 정보 수정 실패");
		}
	}


	public void deleteMember(String deleId) {

		int result = 0;
		result = service.deleteMember(deleId);
		
		if(result>0) {
			new MemberView().displaySuccess("회원 탈퇴 성공");
		}else {
			new MemberView().displayFail("회원 탈퇴 실패");
		}
	}
	

}
