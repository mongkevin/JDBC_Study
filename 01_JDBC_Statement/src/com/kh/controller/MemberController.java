package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.MemberDao;
import com.kh.model.vo.Member;
import com.kh.view.MemberView;

//Controller: View를 통해 요청받은 기능을 수행 및 전달하는 역할 넘겨받은 데이터를 가공처리 후 DAO 또는 Service로 전달하는 역할
//            Dao에서 처리된 결과값을 받아 결과를 토대로 사용자에게 보여줄 화면을 지정하는 역할까지 수행한다.
public class MemberController {

	public void insertMember(String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby) {

		//1.전달된 데이터들을 Member객체에 담아서 전달하기 - 가공처리
		Member m = new Member(userId,userPwd,userName,gender,age,email,phone,address,hobby);
		
		//2.가공처리한 멤버 객체를 DAO에 전달하여 데이터베이스에 저장하기 요청
		int result = new MemberDao().insertMember(m); //result를 결과값으로 돌려받음
		
		//3.전달받은 결과값에 따라 사용자에게 보여줄 화면 지정(응답)
		if(result>0) {//성공시
			//성공 메세지를 띄워주는 메소드
			new MemberView().displaySuccess("회원 추가 성공");
		}else { //실패시
			new MemberView().displayFail("회원추가 실패");
		}
	}

	//회원 전체조회 요청 메소드
	public void selectAll() {
		
		ArrayList<Member> list = new MemberDao().selectAll(); //dao에게 회원전체조회 요청 보내기
		
		//조회 결과가 있는지 없는지에 따라 사용자에게 보여줄 화면 지정
		if(list.isEmpty()) { //조회결과가 없을때(리스트가 비어있을때)
			new MemberView().displayNodata("전체 조회 결과가 없습니다.");
		}else {//조회결과가 있을때
			new MemberView().displayList(list);
		}
		
	}

	//회원 아이디로 조회 요청 메소드
	public Member selectById(String userId) {
		//조회결과 Member 전달받기
		Member m = new MemberDao().selectById(userId);
		
		//조회결과가 있는지 없는지에 따라 사용자에게 보여줄 화면 선택(view)
		if(m==null) { //조회결과가 없는 경우
			new MemberView().displayNodata(userId+"에 해당하는 조회 결과가 없습니다. ");
		}else { //조회결과가 없는 경우
			new MemberView().displayOne(m);
		}
		return m;
	}

	//회원 이름으로 조회 요청 메소드
	public ArrayList<Member> selectByName(String name) {

		//이름으로 조회한 결과 list전달 받기
		ArrayList<Member> list = new MemberDao().selectByName(name);
		//조회결과가 있는지 없는지에 따라 사용자에게 보여줄 화면 선택
		if(list.isEmpty()) { //비어있으면(조회결과 없으면)
			new MemberView().displayNodata(name+"으로 조회된 결과가 없습니다.");
		}else { //조회결과가 잆다면(여러개가 있을수 있음)
			new MemberView().displayList(list);
		}
		return null;
	}

	public void updateMember(String userId, String newPwd, String newEmail, String newPhone, String newAddress) {
		//Member 객체에 담아가기(가공처리)
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(newPwd);
		m.setEmail(newEmail);
		m.setPhone(newPhone);
		m.setAddress(newAddress);
		
		//데이터 가공처리 한 m 객체변수 DAO에 전달하기
		int result = new MemberDao().updateMember(m);
		
		//결과에 따른 화면 지정
		if(result>0) { //성공
			new MemberView().displaySuccess("회원 정보 수정 완료");
		}else { //실패
			new MemberView().displayFail("회원 정보 수정 실패");
		}
	}

	//회원 탈퇴 요청 메소드
	public void deleteMember(String userId) {
		int result = new MemberDao().deleteMember(userId);
		
		if(result>0) {//성공
			new MemberView().displaySuccess(userId+"회원 탈퇴 성공");
		}else {//실패
			new MemberView().displayFail("회원 탈퇴 실패");
		}
	}

}
