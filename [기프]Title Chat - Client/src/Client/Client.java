package Client;

import java.net.Socket;

import Addon.MySocket;

public class Client {
	
	//작성자 2016244009 전재욱
	//프로그램 명 : Title Chat - Client
	//사용자 자신이 원하는 주제를 선택하여 채팅방을 생성하거나 참여하여 채팅하는 프로그램
	//버젼 1.0.0

	public static MySocket server; //서버와의 통신용 소켓
	public static String id = null; //사용자 id
	public static String pw = null; //사용자 pw
	public static String name = null; //사용자 이름
	public static String telNum = null; //사용자 연락처
	public static String nickName = null; //사용자 닉네임
	public static String status = "접속중"; //사용자 현재 화면
	final public static String[] titleList = {"" ,"게임", "요리", "여행", "생활","학습"}; //주제 목록
	
	public static void main(String args[]) {
		ConnectingGUI connect = new ConnectingGUI(); //접속중 프레임 실행
	}
	
}
