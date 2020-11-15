package Server;

import java.sql.*;

public class MyDatabase { //�����ͺ��̽� ��ſ�
	
	String url = "jdbc:mysql://127.0.0.1:3306/titlechat?serverTimezone=Asia/Seoul"; //������ ���̽� �ּ�
	String rootId = "root"; //��Ʈ id
	String rootPw = "6789"; //��Ʈ pw
	Connection con; //�����ͺ��̽� ��ſ�
	Statement stmt; //�����ͺ��̽� ��� ���ؿ뤷
	
	public MyDatabase() { //���� �� �ʱ�ȭ
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(java.lang.ClassNotFoundException e) {
			e.printStackTrace();
			return;
		} 
		try {
			con = DriverManager.getConnection(url, rootId, rootPw); //������ ���̽� �α���
			stmt = con.createStatement();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
	}
	
	public String getPassword(String id) { //ID���� �̿��Ͽ� LoginInfo(�α������� ���̺�)���� PW �޾ƿ�
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
	
	public boolean insertRegisterData(String id, String pw) { //LoginInfo(�α������� ���̺�)�� ���ο� ID,PW���� �Ǵ� PW�� ����
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
	
	public String[] getUserInfo(String id) { //UserInfo(����� ���� ���̺�)���� ID���� �̿��Ͽ� ����(�̸�, ����ó, �г���) �ҷ���
		String args[] = new String[3];
		try {
			ResultSet result = stmt.executeQuery("SELECT * FROM UserInfo where ID = '"+id+"'");
			if(!result.next()) return null;
			args[0] = result.getString("�̸�");
			args[1] = result.getString("����ó");
			args[2] = result.getString("�г���");
		} catch (SQLException e) {		
			args = null;
			e.printStackTrace();
		}
		return args;
	}
	
	public boolean insertUserInfo(String id, String name, String telNum, String nickName) { //UserInfo(����� ���� ���̺�) ���ο� �� ���� �Ǵ� ����
		try {
			String userInfo[] = getUserInfo(id);
			if(userInfo == null) {
				stmt.executeUpdate("INSERT INTO USERINFO VALUES ('"+id+"','"+name+"','"+telNum+"','"+nickName+"')");
			} else {
				stmt.executeUpdate("UPDATE USERINFO SET �̸� = '"+name+"', ����ó = '"+telNum+"', �г��� = '"+nickName+"' WHERE ID = '"+id+"'");
			}
			return true;
		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			return false;
		}
	}
	
}
