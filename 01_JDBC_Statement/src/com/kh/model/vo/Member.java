package com.kh.model.vo;

import java.sql.Date;

/*
 * VO (Value Object)
 * DB 테이블의 한 행에 대한 데이터를 기록할 수 있는 저장용 객체 
 * 
 * VO조건
 * 1) 갭슐화 필수 
 * 2) 기본생성자 및 매개변수 생성자 생성
 * 3) 모든 필드에 대한 getter/setter 메소드 작성
 * 
 * 유사용어 
 * DTO(Data Transfer Object)
 * DO(Domain Object)
 * */



public class Member {
	
	//필드부: DB테이블의 컬럼 정보와 유사하게 작업
	private int userNo;       		//	USERNO	NUMBER
	private String userId;    		//	USERID	VARCHAR2(15 BYTE)
	private String userPwd;  	    //	USERPWD	VARCHAR2(20 BYTE)
	private String userName;		//	USERNAME	VARCHAR2(20 BYTE)
	private String gender;			//	GENDER	CHAR(1 BYTE)
	private int age;				//	AGE	NUMBER
	private String email;			//	EMAIL	VARCHAR2(30 BYTE)
	private String phone;			//	PHONE	CHAR(11 BYTE)
	private String address;			//	ADDRESS	VARCHAR2(100 BYTE)
	private String hobby;			//	HOBBY	VARCHAR2(50 BYTE)
	private	Date enrolldate;		//	ENROLLDATE	DATE
	
	@Override
	public String toString() {
		return "Member [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName
				+ ", gender=" + gender + ", age=" + age + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", hobby=" + hobby + ", enrolldate=" + enrolldate + "]";
	}

	public Member() {
		super();
	}
	
	//전달시 필요한 데이터 담아서 생성할 생성자 만들기
	public Member(String userId, String userPwd, String userName, String gender, int age, String email, String phone,
			String address, String hobby) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
	}
	
	public Member(int userNo, String userId, String userPwd, String userName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrolldate) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrolldate = enrolldate;
	}


	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String string) {
		this.userId = string;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public Date getEnrolldate() {
		return enrolldate;
	}

	public void setEnrolldate(Date enrolldate) {
		this.enrolldate = enrolldate;
	}
	
	
	
	

}
