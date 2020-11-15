package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MySocket extends Thread { //서버와 연결된 클라이언트들을 관리하기 위한 MySocket
	Socket socket; //클라이언트 소켓
	OutputStream outStream; //통신용
	DataOutputStream dataOutStream;//통신용
	InputStream inStream;//통신용
	DataInputStream dataInStream;//통신용
	Server server; //서버 객체
	
	boolean stopFlag; //쓰레드 중지 flag
	
	MySocket(Socket _s, Server server) { //생성 및 초기화
		try {
			this.socket = _s;
			this.server = server;
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
		} catch (Exception e) {
			
		}
	}

	public void run() {
		try {
			server.printLog(socket.getInetAddress()+" >> Server : 연결됨"); //서버에 연결됨 메시지 출력
			
			dataOutStream.writeUTF("ACT"); //클라이언트에 접속 성공 메시지 전달
			while (!stopFlag) { //반복
				String msg = dataInStream.readUTF(); //클라이언트에서 메시지 수신
				String args[] = msg.split("§"); //메시지 파싱
				if(args.length > 0) { //메시지가 정상적으로 넘어왔다면 1이상임
					if(args[0].equals("SCM")) { //만약 채팅 메시지 전달 신호라면(SCM) 비밀성 보장을 위하여  서버 로그화면에 메시지는 출력하지 않음
						server.printLog(socket.getInetAddress()+" >> Server : SCM");
					}else { //그 외의 신호라면 서버로그에 그대로 출력
						server.printLog(socket.getInetAddress()+" >> Server : "+msg);
					}	
				} 
				server.analysisMessage(this, msg); //수신한 신호 분석
			}
		} catch (Exception e) { //통신 실패시
			server.quitRoom(this); //해당 클라이언트 채팅방 퇴장 처리
			server.clients.remove(this); //서버 클라이언트 목록에서 삭제
			server.printLog(socket.getInetAddress()+" >> Server : "+"연결종료"); //로그에 연결종료 메시지 출력
		}
	}

	public void sendMessage(String msg) { //클라이언트에 메시지 전송
		try {
			server.printLog("Server >> "+socket.getInetAddress()+" : "+msg); //로그에 전송 메시지 출력
			dataOutStream.writeUTF(msg); //메시지 전송
		} catch (Exception e) { 
			System.out.println("Server> 메세지 전송 에러 - " + socket.getInetAddress());
		}
	}
}
