package Server;

import java.sql.*;

public class MyDatabase { //데이터베이스 통신용
	
	String url = "jdbc:mysql://127.0.0.1:3306/titlechat?serverTimezone=Asia/Seoul"; //데이터 베이스 주소
	String rootId = "root"; //루트 id
	String rootPw = "6789"; //루트 pw
	Connection con; //데이터베이스 통신용
	Statement stmt; //데이터베이스 명령 실해용ㅇ
	
	public MyDatabase() { //생성 및 초기화
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		try {
			con = DriverManager.getConnection(url, rootId, rootPw); //데이터 베이스 로그인
			stmt = con.createStatement();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	public String getPassword(String id) { //ID값을 이용하여 LoginInfo(로그인정보 테이블)에서 PW 받아옴
		String pw = null;
		try {
			ResultSet result = stmt.executeQuery("SELECT PW FROM Logininfo where ID = '"+id+"'");
			if(!result.next()) return null;
			pw = result.getString("pw");
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		return pw;
	}
	
	public boolean insertRegisterData(String id, String pw) { //LoginInfo(로그인정보 테이블)에 새로운 ID,PW삽입 또는 PW값 수정
		try {
			String chekcpw = getPassword(id);
			if(chekcpw == null) {
				stmt.executeUpdate("INSERT INTO LOGININFO VALUES ('"+id+"','"+pw+"')");
			} else {
				stmt.executeUpdate("UPDATE LoginInfo SET PW = '"+pw+"' WHERE ID = '"+id+"'");
			}
			return true;
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return false;
		}
	}
	
	public String[] getUserInfo(String id) { //UserInfo(사용자 정보 테이블)에서 ID값을 이용하여 정보(이름, 연락처, 닉네임) 불러옴
		String args[] = new String[3];
		try {
			ResultSet result = stmt.executeQuery("SELECT * FROM UserInfo where ID = '"+id+"'");
			if(!result.next()) return null;
			args[0] = result.getString("이름");
			args[1] = result.getString("연락처");
			args[2] = result.getString("닉네임");
		} catch (SQLException e) {		
			args = null;
			e.printStackTrace();
		}
		return args;
	}
	
	public boolean insertUserInfo(String id, String name, String telNum, String nickName) { //UserInfo(사용자 정보 테이블에) 새로운 값 삽입 또는 수정
		try {
			String userInfo[] = getUserInfo(id);
			if(userInfo == null) {
				stmt.executeUpdate("INSERT INTO USERINFO VALUES ('"+id+"','"+name+"','"+telNum+"','"+nickName+"')");
			} else {
				stmt.executeUpdate("UPDATE USERINFO SET 이름 = '"+name+"', 연락처 = '"+telNum+"', 닉네임 = '"+nickName+"' WHERE ID = '"+id+"'");
			}
			return true;
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return false;
		}
	}
	
}
