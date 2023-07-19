# JDBC_Study
Study JAVA Database Connectivity  
Oracle sqldeveloper을 기반으로 작성되어 총 5개 프로젝트로 구성되어 있다.

---
## 00_JDBCTEST
### JDBC용 객체 
-Connection: DB의 연결정보를 담고있는 객체(IP주소, PORT번호, 계정명, 비밀번호)  
-(Prepared)Statement: 해당 DB에 SQL문을 전달하고 실행한 후 결과를 받아내는 객체  
-ResultSet: 만일 실행한 SQL문의 SELECT문일 경우 조회된 결과들이 담겨있는 객체   

JDBC 처리 순서  
1) JDBC Driver 등록: 해당 DBMS가 제공하는 클래스 등록  
2) Connection 생성: 접속하고자 하는 DB에 정보를 입력하여 DB에 접속하여 생성   
3) Statement 생성: Connection 객체를 이용하여 생성   
4) SQL문을 전달하면서 실행: Statement 객체를 이용해서 SQL문을 실행  
    -SELECT문일 경우 excuteQuery(); 메소드를 사용.  
    -나머지 DML문일 경우 excuteUpdate(); 메소드 사용.   
    
5)결과 받기  
6_1) SELECT문 - ResultSet 객체(조회된 데이터가 담겨있다)로 받아준다.  
6_2) SELECT문으로 실행하여 조회된 결과를 담은 ResultSet에 있는 데이터를 추출하여 VO객체에 담기.  
      만약 여러행이 조회된다면? ArrayList에 묶어서 담아가기.  
6_3) DML문 - int형 변수로 처리된 행수 전달받기.  
6_4) DML문이 실행됐다면 트랜잭션 처리를 해주어야한다. (성공이면 commit; 실패면 rollback;)  
7) 사용 완료한 JDBC용 객체들 자원 반납(close) -> 생성의 역순으로 반납한다.  

**INSERT문으로 데이터 입력하기**
```java
//자원 반납을 위해 미리 객체 선언만 해두기
Connection conn = null; //DB접속 정보를 담고 있는 객체
Statement stmt = null; //sql문 전달하여 실행 후 결과 돌려주는 객체
int result = 0; //처리된 결과 행수 전달 받을 변수
String sql = "INSERT INTO TEST VALUES(2,'김박사',SYSDATE)"; //처리할 sql문

try {
  //1) jdbc driver 등록(oracle.jdbd.driver.OracleDriver)
  Class.forName("oracle.jdbc.driver.OracleDriver");
  //ClassNotFoundException
  //System.out.println("jdbc driver 등록 성공");
			
  //2)Connection 객체 생성: DB에 연결(url,계정명,비밀번호)
  //-ip주소1521(포트번호)xe(버전)
  conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
		    
  //자동커밋여부 설정 -- 직접 트랜잭션 처리를 하겠다.
  conn.setAutoCommit(false); //true 값이 기본값으로 자동으로 커밋이 된다.
		    
  //3)Statement 객체 생성
  stmt = conn.createStatement();
		    
  //4)sql문을 전달하고 결과값 받기(처리된 행수)
  result = stmt.executeUpdate(sql);
		    
  //5)트랜잭션 처리하기 (성공 실패 여부 확인 후 commit/rollback)
  if(result>0) { //성공했을 경우 처리된 행수가 1이상인 경우 (commit)
    conn.commit();
  }else { //실패했을 경우 되돌리기(rollback)
    conn.rollback();
  }
		    
} catch (ClassNotFoundException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
} catch (SQLException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
} finally {
  //6)자원 반납하기
  try {
    stmt.close();
    conn.close();
  } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
}
		
  if(result>0){
    System.out.println("성공적으로 데이터가 추가되었습니다.");
  }else {
    System.out.println("데이터가 추가에 실패하였습니다.");
  }
		
}
```

**SELECT문으로 데이터 조회 하기**
```java
//TEST 테이블에 있는 데이터 조회해오기
//SELECT문 - ResultSet(조회된 행객체가 담겨온다)
		
//필요한 변수들 세팅하기
Connection conn = null;
Statement stmt = null;
ResultSet rset = null; //결과행 담을 객체 
		
String sql = "SELECT * FROM TEST";
		
try {
  //1)드라이버 등록 
  Class.forName("oracle.jdbc.driver.OracleDriver");
			
  //2)Connection 객체 생성
  conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","JDBC","JDBC");
			
  //3)Statement 객체 생성
  stmt = conn.createStatement();
			
  //4)sql문 전달 및 결과값 받기 select문의 결과는 ResultSet에 담겨져 온다
  //select문을 전달하는 메소드는 executeQuery()이다
  rset = stmt.executeQuery(sql);
			
  //5)ResultSet에 담겨져 있는 데이터를 꺼내보자
  //현재 참조하고 있는 rset으로부터 어떤 컬럼에 해당하는 값을 어떤 타입에 담을 것인지 제시
  //db에 컬럼명을 제시하면 된다
			
			
  while(rset.next()) {//rset.next(): 커서 위치 옮기기 - 옮긴 후 대상이 있으면 true 없으면 false
    int tno = rset.getInt("TNO"); //TNO에 있는 데이터를 자바변수tno에 담겠다.
    String tname = rset.getString("TNAME");
    Date tdate = rset.getDate("TDATE"); //java.sql 패키지에 있는 Date
				
    System.out.println(tno + " " + tname + " " + tdate);
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
  } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
			
}
```
---
## 01_JDBC_Statement
![mvc](./image/mvc.png)  
### Controller
View를 통해 요청받은 기능을 수행 및 전달하는 역할 넘겨받은 데이터를 가공처리 후 DAO 또는 Service로 전달하는 역할  
Dao에서 처리된 결과값을 받아 결과를 토대로 사용자에게 보여줄 화면을 지정하는 역할까지 수행한다. 
또는 API에서 클라이언트의 요청을 처리하고 응답을 반환하는 컴포넌트이다.  

Controller의 주요 기능은 다음과 같다:  

1. 클라이언트 요청의 처리: Controller는 클라이언트로부터의 HTTP 요청을 받아들이고, 이를 처리하기 위해 적절한 액션 또는  
   메서드를 호출한다. 이를 통해 요청에 따른 비즈니스 로직을 실행하고, 데이터를 처리하며, 다른 컴포넌트와의 상호 작용을 조정한다.  

2. 데이터 전달과 가공: Controller는 클라이언트로부터 받은 요청에 필요한 데이터를 모델이나 서비스와 같은 다른 컴포넌트로부터  
   가져와서 가공한다. 이를 통해 필요한 데이터를 추출하거나 가공하여 뷰에 전달하거나 API 응답으로 반환한다.  

3. 비즈니스 로직의 호출: Controller는 비즈니스 로직을 담당하는 다른 컴포넌트나 서비스를 호출한다. 이를 통해 요청을 처리하고  
   필요한 작업을 수행한다. 예를 들어, 데이터베이스 조작, 외부 시스템과의 상호 작용, 데이터의 유효성 검사 등을 수행할 수 있다. 

응답의 반환: Controller는 비즈니스 로직의 결과에 따라 적절한 응답을 생성하고 클라이언트에 반환한다. 이는 HTML 페이지, JSON 데이터,  
파일 다운로드 등의 형태일 수 있다. 응답을 생성하고 반환하는 작업은 주로 뷰나 템플릿 엔진과 함께 수행된다.  

Controller는 일반적으로 클라이언트와 직접적으로 상호 작용하고, 요청의 라우팅과 처리를 담당한다.   
이는 애플리케이션의 흐름을 제어하고 요청을 적절한 컴포넌트로 전달하여 처리하는 역할을 수행한다.   

### DAO(DATA ACCESS OBJECT)
DAO (Data Access Object)는 데이터베이스 또는 다른 데이터 저장소에 대한 접근을 추상화하는 객체이다.  
DAO는 비즈니스 로직과 데이터베이스 간의 결합도를 낮추고, 데이터베이스에 대한 접근을 캡슐화하여   
데이터베이스 조작에 대한 세부 사항을 숨긴다.  

DAO는 다음과 같은 주요 기능을 제공한다:    

1. 데이터베이스 접근 추상화: DAO는 데이터베이스에 접근하기 위한 CRUD (Create, Read, Update, Delete) 연산을 캡슐화한다.  
   이를 통해 비즈니스 로직은 DAO를 통해 데이터베이스와 상호 작용할 수 있으며, 데이터베이스의 세부 사항은 DAO 내부에 숨긴다.  

2. 데이터베이스 연산의 일관성 유지: DAO는 여러 개체 또는 테이블 간의 관계를 관리하고, 데이터베이스 연산을 일관성 있게 유지한다.  
   예를 들어, 여러 테이블에 걸쳐 복잡한 연산이 필요한 경우 DAO는 이러한 연산을 조율하고 일관된 결과를 반환한다.  

3. 보안 및 트랜잭션 관리: DAO는 데이터베이스 접근에 대한 보안을 제공하고, 트랜잭션 관리를 처리한다.  
   트랜잭션은 여러 데이터베이스 연산을 하나의 원자적인 작업으로 묶어 데이터의 일관성을 보장한다.  
   DAO는 이러한 트랜잭션을 시작하고 완료하며, 롤백 등의 기능을 제공한다.  

4. 테스트 용이성: DAO는 데이터베이스와의 상호 작용을 캡슐화하기 때문에, 비즈니스 로직의 테스트를 용이하게 만든다. DAO를 모킹  
   (mocking) 또는 스텁(stub)으로 대체하여 데이터베이스에 의존하지 않고도 테스트를 수행할 수 있다.  

DAO는 주로 객체 지향 프로그래밍에서 사용되며, 데이터베이스의 특정 기술에 종속되지 않도록 설계되어야 한다.  
이를 통해 데이터베이스를 변경하더라도 DAO 인터페이스를 수정하지 않고도 기존 코드를 유지할 수 있다.  

### VO (Value Object)
DB 테이블의 한 행에 대한 데이터를 기록할 수 있는 저장용 객체  
VO조건
1) 갭슐화 필수 
2) 기본생성자 및 매개변수 생성자 생성
3) 모든 필드에 대한 getter/setter 메소드 작성

유사용어 
 * DTO(Data Transfer Object)
 * DO(Domain Object)

VO, DTO, DO는 모두 소프트웨어 개발에서 주로 사용되는 데이터 전송 객체(Data Transfer Object) 패턴과 관련된 용어이다.  
각각의 용어는 다음과 같은 차이점을 가지고 있다:   

1. VO (Value Object):  
   - 값 객체라고도 불리며, 도메인 모델에서 사용되는 객체이다.  
   - 주로 읽기 전용 데이터를 나타내며, 데이터의 상태를 변경하는 메서드를 가지지 않는다.  
   - 도메인 객체 간 데이터 교환 및 데이터의 불변성을 보장하는 데 주로 사용된다.  
   - 예를 들어, 사용자의 이름, 나이, 주소 등을 나타내는 객체가 VO로 사용될 수 있다.  

2. DTO (Data Transfer Object):  
   - 데이터 전송 객체라고도 불리며, 계층 간 데이터 교환을 위해 사용되는 객체이다.  
   - 주로 여러 계층(예: 클라이언트와 서버) 사이에서 데이터를 전송하는 데 사용된다.  
   - 일반적으로 데이터의 필드만을 가지고 있으며, 비즈니스 로직을 포함하지 않된다.  
   - 예를 들어, 사용자의 정보를 서버로 전송하기 위해 사용되는 객체가 DTO로 사용될 수 있다.  

3. DO (Domain Object):  
   - 도메인 객체라고도 불리며, 비즈니스 도메인을 나타내는 객체이다.  
   - 주로 비즈니스 로직을 포함하고 도메인 모델의 핵심 역할을 수행한다.  
   - 도메인 객체는 응용 프로그램의 도메인 영역에 해당하는 로직을 캡슐화하고 데이터를 관리한다.  
   - 예를 들어, 주문, 상품, 결제 등을 나타내는 객체가 DO로 사용될 수 있다.  

요약하자면, VO는 읽기 전용 값 객체로서 도메인 모델에서 사용되며,  
DTO는 계층 간 데이터 전송을 위해 사용되는 객체로 비즈니스 로직을 포함하지 않는다.  
DO는 비즈니스 도메인을 나타내는 객체로서 비즈니스 로직을 포함하고 데이터를 관리한다.  

### Service:  
기본의 DAO에서 하던 역할을 분담하는 곳   
 * 컨트롤러에서 서비스 호출(Connection객체 생성) 후 서비스를 거쳐 DAO로 넘어간다.  
 * DAO호출할때 컨넥션 객체와 기존에 넘기고자했던 데이터를 같이 매개변수로 넘겨준다.  
 * DAO 처리가 끝나면 서비스단에서 결과에 따른 트랜잭션 처리도 해준다.  
 * 서비스단을 추가함으로써 DAO에는 SQL문 처리 구문만 남게 된다. (역할분담)  

비즈니스 로직을 구현하고 제공하는 컴포넌트를 의미한다. 서비스는 도메인에 대한 특정한 작업을 수행하고, 여러 컴포넌트 간의 상호 작용을 조정하며, 데이터의 가공 및 조작을 담당한다.  

서비스의 주요 특징과 역할은 다음과 같다:  

1. 비즈니스 로직 수행: 서비스는 애플리케이션의 비즈니스 로직을 수행하는 역할을 한다. 이는 사용자의 요청에 따라 필요한 작업을 처리하거나 도메인의 규칙과 로직을 적용하는 등의 작업을  
   포함한다.  

2. 데이터 조작 및 가공: 서비스는 데이터를 조작하고 가공하여 필요한 형식이나 구조로 변환한다. 데이터베이스와의 상호 작용, 외부 API 호출, 파일 조작 등 다양한 데이터 조작 작업을  
   수행할 수 있다.  

3. 다른 컴포넌트와의 상호 작용 조정: 서비스는 다른 컴포넌트와의 상호 작용을 조정하고 조합한다. 이는 데이터 액세스 객체(DAO), 외부 서비스, 메시징 시스템 등과의 통신을 포함할 수 
   있다. 

4. 트랜잭션 관리: 서비스는 여러 개의 연산이 하나의 원자적인 작업으로 처리되어야 하는 경우 트랜잭션 관리를 수행한다. 이는 데이터의 일관성과 무결성을 보장하기 위해 사용된다.  

5. 의존성 주입: 서비스는 종종 의존성 주입(Dependency Injection)을 사용하여 필요한 의존성을 주입받는다. 이를 통해 서비스는 다른 컴포넌트와의 결합도를 낮추고 테스트 용이성을 
   개선할 수 있다.  

서비스는 주로 비즈니스 로직의 중심 역할을 수행하며, 컨트롤러(Controller)나 뷰(View)와 같은 다른 컴포넌트와 협력하여 애플리케이션의 기능을 구현한다. 서비스는 재사용성과 모듈성을   
갖도록 설계되어야 하며, 일반적으로 도메인 주도 설계(Domain-Driven Design) 등의 개념과 함께 사용된다.  

~~더 자세한 이야기: [말만 하는 개발자](https://mongkevin.tistory.com/)~~

---
## 02_JDBC_PreparedStatement
### Statement 와 PreparedStatement의 차이점
관계는 Statement가 부모타입 PreparedStatement는 자식타입
1) Statement는 완성된 SQL문을 전달, PreparedStatement 미완성된 SQL을 전달
2) Statement 객체 생성시 stmt = conn.createStatement();
   PreparedStatement객체 생성시 pstmt = conn.prepareStatement(sql);
   
3) Statement로 SQL문 실행시 결과 - stmt.executeXXX();
   PreparedStatement로 SQL문 실행시 ?로 표현한 빈공간을 채워줘야한다.
   pstmt.setString(?위치,값);
   pstmt.setInt(?위치, 값);
   결과 - pstmt.executeXXX();

**INSERT문**
```java
	public int insertMember(Member m) {
		int result = 0; //결과 처리 행수 담을 변수
		Connection conn = null; //연결객체
//		Statement stmt = null; //기존사용방식
		PreparedStatement pstmt = null; //위치 홀더를 이용할 수 있는 객체방식
		
		String sql = "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE )";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			//1)드라이버 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2)Connection 객체 생성
			conn = DriverManager.getConnection(url,uId,uPwd);
			conn.setAutoCommit(false);
			//3)PreparedStatement 객체 생성(미완성 상태인 sql문을 먼저 전달하며 생성)
			pstmt = conn.prepareStatement(sql);
			//4)미완성 SQL문일 경우에 위치홀더에 데이터 넣어서 완성시켜주기
			//pstmt.setXXX(?위치, 값)
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());
			
			//5)완성된 SQL문 전달 후 결과값 받기
			result = pstmt.executeUpdate();
			
			//6)트랜잭션 처리
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
```

**SELECT문**
```java
	public ArrayList<Member> selectByName(String name) {
		ArrayList<Member> list = new ArrayList<Member>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//방법 1) ? 위치 홀더가 문자열처리 되기 때문에 형식 오류가 발생한다 ex)'%'홍길동'%'
		//oracle에서 문자열 덧셈연산을 사용하여 하나의 문자열로 만들기
		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE '%'||?||'%'";
		
		//방법2) 위치홀더를 마련해놓고 자바에서 %%기호까지 추가하여 대입하기
		//"%홍길동%"로 만들어서 위치홀더에 대입
//		String sql = "SELECT * FROM MEMBER WHERE USERNAME LIKE ?";
//		pstmt.setString(1, "%"+name+"%");
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				Member m =  new Member(rset.getInt("USERNO"),
						rset.getString("USERID"),
						rset.getString("USERPWD"),
						rset.getString("USERNAME"),
						rset.getString("GENDER"),
						rset.getInt("AGE"),
						rset.getString("EMAIL"),
						rset.getString("PHONE"),
						rset.getString("ADDRESS"),
						rset.getString("HOBBY"),
						rset.getDate("ENROLLDATE"));

				list.add(m);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
```

**UPDATE문**
```java
	public int updateMember(Member m) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql =  "UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, PHONE = ?, ADDRESS = ?";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uId = "JDBC";
		String uPwd = "JDBC";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url,uId,uPwd);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			result = pstmt.executeUpdate();
			
			if(result>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
				
		return result;
	}
```

---
## 03_JDBC_Template

JDBC 과정 중 반복적으로 쓰이는 구문들을 각각의 메소드로 정의해둘 곳
"재사용할 목적" 으로 공통 템플릿 작업 진행
 
해당 클래스에서 모든 메소드는 다 static메소드로 작성한다.  
-싱글톤 패턴을 이용할 것이기 때문이다  
-싱글톤 패턴: 메모리 영역에 한번 객체를 등록시켜 해당 객체를 게속 사용하는 개념  
![Singletom](./image/singleton.png)  
### 싱글턴 패턴(Singleton Pattern)  
은 소프트웨어 디자인 패턴 중 하나로, 클래스의 인스턴스를 오직 하나만 생성하고, 이에 대한 전역적인 접근점을 제공하는 패턴이다.   
이는 해당 클래스의 인스턴스가 오직 하나만 존재하도록 보장하며, 다른 객체들은 항상 동일한 인스턴스에 접근할 수 있다. 

싱글턴 패턴의 주요 특징은 다음과 같다:   

1. 프라이빗 생성자: 싱글턴 클래스는 외부에서 직접 인스턴스를 생성할 수 없도록 프라이빗 생성자를 가지고 있다. 이는 외부에서 싱글턴 클래스의 인스턴스를 새로 생성하는 것을 방지한다.   

2. 정적 메서드를 통한 인스턴스 접근: 싱글턴 클래스는 정적 메서드를 제공하여 클래스의 유일한 인스턴스에 접근할 수 있도록 한다. 이를 통해 어디서든 동일한 인스턴스에 접근할 수 있다.   

3. 전역적인 접근점: 싱글턴 인스턴스는 전역적인 접근점을 제공하기 때문에, 다른 객체들은 항상 동일한 인스턴스를 사용할 수 있다. 이는 상태 공유와 효율적인 자원 활용 등의 장점을  
   제공할 수 있다.  

싱글턴 패턴은 다음과 같은 상황에서 유용하게 사용될 수 있다:  

- 리소스 관리: 공유 리소스에 대한 동시 액세스를 관리하거나, 데이터베이스 연결, 로깅, 캐싱과 같은 자원을 효율적으로 활용하기 위해 싱글턴 패턴을 사용할 수 있다.  

- 상태 유지: 애플리케이션 전체에서 동일한 상태를 유지해야 하는 경우, 싱글턴 패턴을 사용하여 상태를 관리하고 동기화할 수 있다.  

- 설정 관리: 애플리케이션의 설정 정보를 로드하고 전역적으로 접근할 수 있도록 싱글턴 패턴을 사용할 수 있다.  

**그러나 싱글턴 패턴은 과도한 사용으로 인해 전역 상태와 결합도가 높아질 수 있으며, 테스트하기 어렵고 유연성이 떨어질 수 있다. 따라서 사용 시 신중하게 고려해야 한다.**

-Connection 생성구문
```java
private static Connection conn;
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String ID = "JDBC";
	public static final String PWD = "JDBC";
	
	
	//DB와 접속된 Connection 객체 생성 및 반환해주는 메소드 
	public static Connection getConnection() {
		
		try {
			if(conn==null || conn.isClosed()) { //Connection객체가 null인 경우 생성 아닌경우 필드에 등록된 connection 객체 반환
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection(URL,ID,PWD);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
			 
	//전달받은 JDBC용 객체 자원 반납하는 메소드 생성하기
	//Connection 반납
	public static void close(Connection conn) {
		//주의사항- conn이 null일 경우에는 conn.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(conn!=null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
```
-close 구문  
```java
//stmt.close
	public static void close(Statement stmt) {
		//주의사항- stmt이 null일 경우에는 stmt.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(stmt!=null&&!stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//rset.close()
	public static void close(ResultSet rset) {
		//주의사항- rset이 null일 경우에는 rset.close()를 하면 NullpointerException이 발생한다.
		//또한 닫혀있는 (반납되어 있는)것에 대하여 다시 반납하려하면 오류 발생
		try {//null이 아니고 닫혀있지 않으면
			if(rset!=null&&!rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
```
-commit & rollback (transaction)
```java
	//commit
	public static void commit(Connection conn) {
			try {
				if(conn!=null && !conn.isClosed()) {
					conn.setAutoCommit(false);
					conn.commit();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	//rollback
	public static void rollback(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) {
				conn.setAutoCommit(false);
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
```
 등 공통적으로 사용해야하는 메소드를 작성해두고 사용한다.  

 ---
## 04_JDBC_Properties
기본방식: JDBC Driver 구문, 내가 접속할 DB의 url 정보, 계정명, 비밀번호들을 자바코드 내에 작성함 - 정적코딩방식
-문제점: DBMS가 변경되었을 경우 또는 접속할 url/계정명/비밀번호등이 변경되었을 경우
     		자바 소스코드를 수정해야한다(수정사항 적용하려면 프로그램 재가동 하여야함) 
-유지보수 불편함

-해결방식: DB관련한 정보들을 별도로 관리하는 외부 파일을 만들어 관리
 			    외부파일로 key에 대한 value를 읽어 반영하자
          해당 구문이 실행되는 시점에 외부 파일을 읽어들여 사용하기 때문에 수정사항 바로 적용 가능

**template**
```java
		//파일정보 읽어줄 Properties 객체 생성
		Properties prop = new Properties();
		
		Connection conn = null;
		
		try {
			//파일 정보 읽어오기
			prop.load(new FileInputStream("resources/driver.properties"));
			
			//prop으로부터 getProperty를 사용하여 각 키에 해당하는 벨류를 꺼내오기
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"),
											   prop.getProperty("password"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
```
**properties 파일**
Properties: Map계열의 컬렉션 key + value 세트로 담는게 특징  
Properties는 주로 외부 설정파일을 읽어오기 또는 파일형태로 출력하기 위해 사용된다.  

properties, xml파일로 내보내기 - store(), storeToXML()  
```java
	//resources 폴더 만들기
	
		File f = new File("resources");
		f.mkdir();	
		
		//프로퍼티스 값 세팅
		Properties prop = new Properties();
		prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
		prop.setProperty("url", "jdbc:oracle:thin:@localhost:1521:xe");
		prop.setProperty("username", "JDBC");
		prop.setProperty("password", "JDBC");
		
		System.out.println(prop);
		
//		-------------------driver.properties 파일 생성-------------------
		try {
			prop.store(new FileOutputStream("resources/driver.properties"),"driver properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
//		-------------------QUERY.XML 파일 생성-------------------
		prop.setProperty("insertMember", "INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)");
		
		try {
			prop.storeToXML(new FileOutputStream("resources/query.xml"), "query");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
```
**driver.properties**
```xml
#driver properties
#Tue Mar 14 15:16:28 KST 2023
password=JDBC
driver=oracle.jdbc.driver.OracleDriver
url=jdbc\:oracle\:thin\:@localhost\:1521\:xe
username=JDBC
```
**query.xml**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
<comment>query</comment>
<entry key="insertMember">
INSERT INTO MEMBER VALUES(SEQ_USERNO.NEXTVAL,?,?,?,?,?,?,?,?,?,SYSDATE)
</entry>

<entry key="selectAll">
SELECT * FROM MEMBER
</entry>

<entry key="selectById">
SELECT * FROM MEMBER WHERE USERID = ?
</entry>

<entry key="selectByName">
SELECT * FROM MEMBER WHERE USERNAME = ?
</entry>

<entry key="updateMember">
UPDATE MEMBER SET USERPWD = ?, EMAIL = ?, ADDRESS = ?, HOBBY = ? WHERE USERID = ?
</entry>

<entry key="deleteMember">
DELETE FROM MEMBER WHERE USERID = ?
</entry>
</properties>
```
**DAO실행시 기본생성자**
Service단에서 new MemberDao()를 할때마다 파일을 읽어서 변경사항을 적용시킬 수 있게끔 작업하기
```java
	public MemberDao() {
		try {
			//resources폴더에 있는 query.xml파일 읽어서 prop에 세팅
			prop.loadFromXML(new FileInputStream("resources/query.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
```
추후진행 시 DAO에서 prop.getProperty()구문으로 진행한다.
