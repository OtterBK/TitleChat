package Addon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import Client.ChatroomGUI;
import Client.CheckGUI;
import Client.Client;
import Client.MainMenuGUI;

public class MySocket { //서버와 통신을 위한 MySocket
	Socket socket; //통신 소켓
	OutputStream outStream; //통신을 위한 스트림
	DataOutputStream dataOutStream;//통신을 위한 스트림
	InputStream inStream;//통신을 위한 스트림
	DataInputStream dataInStream;//통신을 위한 스트림
	JFrame caller; //리스닝 모드(서버에서 신호오는걸 계속 감시)를 호출한  GUI 프레임
	String lastMsg = null; //마지막으로 수신한 메시지
	Listener listener; //리스닝모드용 
	
	public MySocket(Socket _s) { //소켓 초기화
		try {
			this.socket = _s;
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startListen(JFrame caller) { //리스닝 모드 시작
		this.caller = caller; //호출한 프레임 저장
		if(listener != null) {
			listener.stopFlag = false;
			return;
		}
		
		listener = new Listener(); //리스너 생성
		listener.start(); //시작
	}
	
	public void stopListen() { //리스닝 모드 중지
		if(listener != null) { //리스너 있다면
			listener.stopFlag = true; //stopFlag 활성화
		}
	}

	public void sendMessage(String msg) { //서버로 메시지 전송
		try {
			dataOutStream.writeUTF(msg);
		} catch (IOException e) { //전송 실패시 알림을 띄우고 프로그램 종료
			//e.printStackTrace();
			CheckGUI cf = new CheckGUI(null, "서버와의 연결이 끊겼습니다.", true, false);	
		}
	}
	
	public String receiveMessage() { //서버에서 메시지 수신
		String msg = null; //전송할 메시지
		if(listener != null && listener.listening) { //만약 리스너 모드가 활성화 되어있다면
			try {
				stopListen(); //리스너를 잠시 멈추고
				listener.join(); //리스너가 끝날때까지 대기(리스너가 대신 메시지를 수신함)
				msg = lastMsg; //리스너가 수신한 메시지를 전송할 메시지에 저장
				startListen(caller); //다시 리스너 시작
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { //리스너 모드가 활성화 되어있지 않다면
			try {
				msg = dataInStream.readUTF(); //서버에서 메시지 수신
				lastMsg = msg; //수신한 메시지를 전송할 메시지에 저장
			} catch (IOException e) { //전송 실패시 알림을 띄우고 프로그램 종료
				//e.printStackTrace();
				CheckGUI cf = new CheckGUI(null, "서버와의 연결이 끊겼습니다.", true, false);
			}
		}
		return msg; //메시지 전송
	}
	
	public void analysisMessage(String msg) { //리스너모드에서 수신한 메시지 분석
		String args[] = msg.split("§"); //문자열 파싱
		String signal = args[0]; //맨 앞 문자열이 신호임
		
		switch(signal) { //신호에 따라서
		
		case "CPC": //채팅방 목록에서 특정 채팅방의 정보 변경됨
			if(Client.status.equalsIgnoreCase("메인메뉴")) { //만약 클라이언트가 메인메뉴 화면이라면
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller;
					mainMenu.editRooms_roominfo(args); //메인메뉴의 특정 채팅방 정보 메소드 호출
				}							
			} 
			break;
		case "CAD": //채팅방 목록에서 새로운 채팅방 추가됨
			if(Client.status.equalsIgnoreCase("메인메뉴")) { //클라이언트가 메인메뉴 화면일때
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller;
					mainMenu.editRooms_add(args); //목록에 해당 채팅방 추가 메소드 호출
				}							
			}
			break;
		case "CDE": //채팅방 목록에서 기존 채팅방이 삭제됨
			if(Client.status.equalsIgnoreCase("메인메뉴")) { //클라이언트 메인메뉴 화면일때
				if(caller instanceof MainMenuGUI) {
					MainMenuGUI mainMenu = (MainMenuGUI) caller; 
					mainMenu.editRooms_del(args); //목록에서 해당 채팅방 삭제 메소드 호출
				}							
			}
			break;
		case "RCM": //채팅방에서 채팅 메시지 출력 신호 수신
			if(Client.status.equalsIgnoreCase("채팅방")) { //클라이언트가 채팅방 화면이라면
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI chatroom = (ChatroomGUI) caller;
					chatroom.printChat(args); //채팅방의 채팅 출력 메소드 실행
				}							
			}
			break;
		case "RAM": //채팅방에서 알림 메시지 출력 신호 수신
			if(Client.status.equalsIgnoreCase("채팅방")) { //클라이언트가 채팅방 화면이라면
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI chatroom = (ChatroomGUI) caller;
					chatroom.printAlert(args[1]); //채팅방의 알림 출력 메소드 실행
				}							
			}
			break;
		case "CRI": //채팅방에서 채팅방 정보 수정 신호 수신
			if(Client.status.equalsIgnoreCase("채팅방")) { //클라이언트가 채팅방 화면이라면
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI room = (ChatroomGUI) caller;
					room.changedRoomInfo(msg); //채팅방 정보 새로고침 메소드 실행
				}							
			}
			break;	
		case "CKR": //채팅방에서 강제퇴장된 메시지 신호 수신
			if(Client.status.equalsIgnoreCase("채팅방")) {//클라이언트가 채팅방 화면이라면
				if(caller instanceof ChatroomGUI) {
					ChatroomGUI room = (ChatroomGUI) caller;
					room.kicked(); //강제퇴장되는 메소드 실행
				}							
			}
			break;	
		case "DMP": //덤프 메시지용
			System.out.println("dump");
			break;
		}
	}
	
	private class Listener extends Thread{
		boolean stopFlag = false;
		boolean listening = true;
		
		public void run() { //리스닝 모드
			try {
				while (!stopFlag) { //stopFlag가 true이면 쓰레드 종료됨
					listening = true; //서버에서 메시지를 넘어오길 대기중이다
					String msg = dataInStream.readUTF();
					listening = false; //현재 서버에서 메시지를 받아 처리할 예정이다
					lastMsg = msg;//수신한 메시지 저장
					analysisMessage(msg); //메시지 분석
				}
				listener = null;
			} catch (IOException e) { //서버와 통신 끊기면 프로그램 종료
				//e.printStackTrace();
				CheckGUI cf = new CheckGUI(null, "서버와의 연결이 끊겼습니다.", true, false);		
			} 
		}
	}
}
