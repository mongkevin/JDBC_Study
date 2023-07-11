package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

public class MemberController {

	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {
		//요청받은 데이터 가공처리 후 DAO에게 전달하기
		//1.전달 받은 데이터가 많으니 Member객체에 담아서 한번에 이동시키기
		Member m = new Member(userId, userPwd, userName, gender, age, email, phone, address,hobby);
		
		//2.가공처리한 데이터 Member객체를 DAO에게 전달하여 데이터 추가요청하기
		int result = new MemberDao().insertMember(m); //처리된 결과 행수 돌려받아서 result에 담아주기
		
		//3.결과값에 따라서 사용자에게 보여줄 화면 지정
		if(result>0) { //성공했을 경우
			//성공 메세지를 띄워줄 화면 메소드 호출
			new MemberView().displaySuccess("회원 추가 성공");
		}else {
			new MemberView().displayFail("회원 추가 실패");
		}
	}

	public Member selectById(String id) {
		Member m = new MemberDao().selectById(id);
		
			
		if(m!=null) {
			new MemberView().displayOne(m);
		}else {
			new MemberView().displayFail(id);
		}
		return m;
	}

	public void selectByName(String name) {
		ArrayList<Member> list = new MemberDao().selectByName(name);
		
		if(list.isEmpty()) {
			new MemberView().displayFail(name);
		}else {
			new MemberView().displayList(list);
		}
	}

	public void updateMember(String newPwd, String newEmail, String newPhone, String newAddress) {
		Member m = new Member();
		m.setUserPwd(newPwd);
		m.setEmail(newEmail);
		m.setPhone(newPhone);
		m.setAddress(newAddress);
		
		int result = new MemberDao().updateMember(m);
		
		if(result>0) { 
			new MemberView().displaySuccess("회원 정보 수정 완료");
		}else { 
			new MemberView().displayFail("회원 정보 수정 실패");
		}
	}

	public void selectAll() {
		ArrayList<Member> list = new MemberDao().selectAll();
		
		if(list.isEmpty()) {
			new MemberView().displayFail("회원 정보 조회 실패");
		}else {
			new MemberView().displayList(list);
		}
		
	}

	public void deleteMember(String id) {
		int result = 0;
		result = new MemberDao().deleteMember(id);
		
		if(result>0) {
			new MemberView().displaySuccess(id + "회원 탈퇴 완료");
		}else {
			new MemberView().displayFail(id + "회원 탈퇴 실패");
		}
	}

}
